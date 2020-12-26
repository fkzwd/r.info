package com.vk.dwzkf.magic_factory.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FactoryIgnore {
}
