package com.vk.dwzkf.shutdown_hook.hook;

import com.vk.dwzkf.shutdown_hook.annotation.ShutdownHookMethod;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ShutdownHookWorker extends Thread {
    private List<ShutdownHookObject> hookObjects = new LinkedList<>();

    @RequiredArgsConstructor
    private static class ShutdownHookObject {
        private final Object hookObject;
        private final List<Method> hookMethods;
    }

    public void registerHook(Object o) {
        List<Method> methods = getMethods(o);
        if (methods.size()==0) {
            log.warn("Can't register bean as shutdownHook. {}", o);
            return;
        }
        hookObjects.add(new ShutdownHookObject(o, methods));
        log.info("Registered as shutdownHook {} methods {}", o, methods);
    }

    private List<Method> getMethods(Object hook) {
        return Arrays.stream(hook.getClass()
                .getMethods())
                .filter(m ->
                        m.isAnnotationPresent(ShutdownHookMethod.class)
                                && m.getReturnType().equals(Void.TYPE)
                                && m.getModifiers() == Modifier.PUBLIC
                                && m.getParameterCount() == 0
                )
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        hookObjects.forEach(o -> o.hookMethods.forEach(m -> {
            try {
                log.info("Invoke {} on {}", m.getName(), o.getClass().getSimpleName());
                m.invoke(o.hookObject); // Было m.invoke(o)
            } catch (Exception e) {
                log.error("While invoke {} on {}", m.getName(), o);
            }
        }));
    }

    @PostConstruct
    public void registerAsShutdownHook() {
        Runtime.getRuntime().addShutdownHook(this);
    }
}
