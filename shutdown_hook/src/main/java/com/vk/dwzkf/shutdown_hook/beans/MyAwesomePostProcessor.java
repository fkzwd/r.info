package com.vk.dwzkf.shutdown_hook.beans;

import com.vk.dwzkf.shutdown_hook.annotation.ShutdownHookMethod;
import com.vk.dwzkf.shutdown_hook.hook.ShutdownHookWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MyAwesomePostProcessor implements BeanPostProcessor {
    private final ShutdownHookWorker shutdownHookWorker;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass()
                .getMethods())
                .filter(m ->
                        m.isAnnotationPresent(ShutdownHookMethod.class)
                                && m.getReturnType().equals(Void.TYPE)
                                && m.getModifiers() == Modifier.PUBLIC
                                && m.getParameterCount() == 0
                )
                .findFirst()
                .ifPresent(m -> shutdownHookWorker.registerHook(bean));
        return null;
    }
}
