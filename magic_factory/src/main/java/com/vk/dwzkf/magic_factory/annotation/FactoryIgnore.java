package com.vk.dwzkf.magic_factory.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FactoryIgnore {
}
