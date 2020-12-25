package com.vk.dwzkf.magic_factory.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Transformable {
    Class<?> value() default Object.class;
}
