package com.vk.dwzkf.magic_factory.factory;

import com.vk.dwzkf.magic_factory.annotation.FactoryIgnore;
import com.vk.dwzkf.magic_factory.annotation.Transformable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.vk.dwzkf.magic_factory.util.ConditionUtil.condition;
import static com.vk.dwzkf.magic_factory.util.CodeUtil.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class MagicFactory {
    private static final Predicate<String> getterPredicate = (s) -> condition(s.startsWith("get"))
            .or(s.startsWith("is"))
            .get();
    private static final Predicate<String> setterPredicate = (s) -> condition(s.startsWith("set"))
            .get();

    private final ApplicationContext applicationContext;
    private final Map<Class<?>, Class<?>> transformableClasses = new HashMap<>();

    @Autowired
    public void register(List<Object> beans) {
        beans.stream().filter(b -> checkAnnotated(b) != null)
                .forEach(o -> {
                    transformableClasses.put(o.getClass().getAnnotation(Transformable.class).value(), o.getClass());
                });

    }

    public <T> T getInstance(Object o) {
        Class<?> instanceClass = checkIsTransformable(o);
        if (instanceClass != null) {
            Object instanceObject = applicationContext.getBean(instanceClass);
            List<Field> instanceFields = getTargetFields(instanceObject);
            List<Field> argObjectFields = getTargetFields(o);

            Map<FieldWrapper, Method> gettersMap = new HashMap<>();
            argObjectFields.forEach(f -> {
                Method m = getGetter(o, f);
                if (m != null) {
                    gettersMap.put(FieldWrapper.of(f),m);
                }
            });

            Map<FieldWrapper, Method> settersMap = new HashMap<>();
            instanceFields.forEach(f -> {
                Method m = getSetter(instanceObject, f);
                if (m != null) {
                    settersMap.put(FieldWrapper.of(f),m);
                }
            });

            gettersMap.forEach((w, m) -> {
                if (settersMap.containsKey(w)) {
                    try {
                        Method setter = settersMap.get(w);
                        Object ret = m.invoke(o);
                        setter.invoke(instanceObject, ret);
                    } catch (Exception e) {
                        log.error("Error while executing {} on'{}' or/and '{}' on'{}'",
                                m,o,instanceClass, settersMap.get(w)
                        );
                    }
                }
            });
            return (T) instanceObject;
        }
        throw new IllegalArgumentException("Object '"+o.toString()+"' have no Annotation @Transformable");
    }

    @Getter
    @Setter
    private static class FieldWrapper {
        private Field field;
        private String methodName;

        public FieldWrapper(Field field) {
            this.field = field;
            this.methodName = field.getName().toLowerCase();
        }

        public static FieldWrapper of(Field field) {
            return new FieldWrapper(field);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FieldWrapper that = (FieldWrapper) o;
            return Objects.equals(methodName, that.methodName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(methodName);
        }
    }

    private Class<?> checkIsTransformable(Object o) {
        return transformableClasses.get(o.getClass());
    }

    private Transformable checkAnnotated(Object o) {
        Transformable transformable = o.getClass().getAnnotation(Transformable.class);
        if (transformable != null && !transformable.value().equals(Object.class)) {
            return transformable;
        }
        return null;
    }

    private List<Field> getTargetFields(Object o) {
        return getTargetFields(o.getClass());
    }

    private List<Field> getTargetFields(Class<?> o) {
        if (o == Object.class) return Collections.emptyList();
        List<Field> fields = Arrays.stream(o.getDeclaredFields())
                .filter(f -> condition(not(f.isAnnotationPresent(FactoryIgnore.class)))
                        .get()
                )
                .collect(Collectors.toList());
        fields.addAll(getTargetFields(o.getSuperclass()));
        return fields;
    }

    private Method getGetter(Object o, Field f) {
        return getFieldAccessor(o, f, getterPredicate, 0, f.getType());
    }

    private Method getSetter(Object o, Field f) {
        return getFieldAccessor(o, f, setterPredicate, 1, null);
    }

    private Method getFieldAccessor(Object o, Field f, Predicate<String> prefixPredicate,
                                    int paramCount, Class<?> returnType) {
        return getFieldAccessor(o.getClass(), f, prefixPredicate, paramCount, returnType);
    }

    private Method getFieldAccessor(Class<?> o, Field f, Predicate<String> prefixPredicate,
                                    int paramCount, Class<?> returnType) {
        if (o == Object.class) return null;
        for (Method m : o.getDeclaredMethods()) {
            if (condition(checkMethodName(m,f))
                    .and(prefixPredicate.test(m.getName()))
                    .and((m.getModifiers() & Modifier.PUBLIC) != 0)
                    .and(condition(returnType==null)
                            .or(returnType==m.getReturnType())
                    )
                    .and(m.getParameterCount() == paramCount)
                    .get()

            ) {
                return m;
            }
        }
        return getFieldAccessor(o.getSuperclass(), f, prefixPredicate, paramCount, returnType);
    }

    private boolean checkMethodName(Method m, Field f) {
        if (f.getType() == boolean.class || f.getType() == Boolean.class) {
            if (f.getName().startsWith("is") && f.getName().length()>2) {
                return m.getName().toLowerCase().endsWith(f.getName().substring(2).toLowerCase());
            }
        }
        return m.getName()
                .toLowerCase()
                .endsWith(f.getName().toLowerCase());
    }
}
