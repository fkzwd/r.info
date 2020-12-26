package com.vk.dwzkf.magic_factory.factory;

import com.vk.dwzkf.magic_factory.annotation.FactoryIgnore;
import com.vk.dwzkf.magic_factory.annotation.Transformable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import static com.vk.dwzkf.magic_factory.util.CodeUtil.not;
import static com.vk.dwzkf.magic_factory.util.ConditionUtil.condition;


@Component
@RequiredArgsConstructor
@Slf4j
public class MagicFactory {
    private final ApplicationContext applicationContext;
    private final Map<Class<?>, Class<?>> transformableClasses = new HashMap<>();

    private final Map<Class<?>, Map<String, Field>> instanceFieldMap = new HashMap<>();
    private final Map<Class<?>, Map<String, Field>> argObjectFieldMap = new HashMap<>();
    private final Map<Class<?>, Object> classToObject = new HashMap<>();

    @Autowired
    public void register(List<Object> beans) {
        beans.stream().filter(b -> checkAnnotated(b) != null)
                .forEach(o -> {
                    transformableClasses.put(o.getClass().getAnnotation(Transformable.class).value(), o.getClass());
                });
    }

    public <T> T getInstance(Object o) {
        Class<?> instanceClass = transformableClasses.get(o.getClass());
        if (instanceClass != null) {
            Object instanceObject = classToObject.get(instanceClass);
            Map<String, Field> instanceFieldMap;
            Map<String, Field> argObjectFieldMap;


            if (classToObject.get(instanceClass) == null) {
                instanceObject = applicationContext.getBean(instanceClass);
                classToObject.put(instanceClass, instanceObject);
            } else {
                instanceObject = clone(instanceObject);
            }
            if (this.instanceFieldMap.get(instanceClass) == null) {
                instanceFieldMap = getTargetFields(instanceObject)
                        .stream()
                        .collect(Collectors.toMap(Field::getName, f -> f));
                argObjectFieldMap = getTargetFields(o)
                        .stream()
                        .collect(Collectors.toMap(Field::getName, f -> f));

                this.instanceFieldMap.put(instanceClass, instanceFieldMap);
                this.argObjectFieldMap.put(o.getClass(), argObjectFieldMap);
            } else {
                instanceFieldMap = this.instanceFieldMap.get(instanceClass);
                argObjectFieldMap = this.argObjectFieldMap.get(o.getClass());
            }

            Object finalInstanceObject = instanceObject;
            instanceFieldMap.forEach((name, field) -> {
                Field f = argObjectFieldMap.get(name);
                if (f != null) {
                    try {
                        f.setAccessible(true);
                        field.setAccessible(true);
                        field.set(finalInstanceObject, f.get(o));
                        field.setAccessible(false);
                        f.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        log.error("Error while setting field {} of {} to {} from {}",
                                name, finalInstanceObject, f.getName(), o);
                        e.printStackTrace();
                    }
                }
            });

            return (T) instanceObject;
        }
        throw new IllegalArgumentException("Object '"+o.toString()+"' have no Annotation @Transformable");
    }

    private Object clone(Object o) {
        Constructor<?> c = o.getClass().getConstructors()[0];
        c.setAccessible(true);
        int parameterCount = c.getParameterCount();
        Object[] args = new Object[parameterCount];
        try {
            Object instance = c.newInstance(args);
            c.setAccessible(false);
            Field[] instanceFields = instance.getClass().getDeclaredFields();
            for (Field field : instanceFields) {
                if ((field.getModifiers() & Modifier.FINAL) != 0) {
                    field.setAccessible(true);
                    field.set(instance, field.get(o));
                    field.setAccessible(false);
                }
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
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
                .filter(f -> not(f.isAnnotationPresent(FactoryIgnore.class))
                )
                .collect(Collectors.toList());
        fields.addAll(getTargetFields(o.getSuperclass()));
        return fields;
    }
}