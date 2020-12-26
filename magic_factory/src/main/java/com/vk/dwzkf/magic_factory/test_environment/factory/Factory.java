package com.vk.dwzkf.magic_factory.test_environment.factory;

import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject;
import com.vk.dwzkf.magic_factory.test_environment.type2.ExecutableObject;

public abstract class Factory<T extends TransferObject, R extends ExecutableObject> {
    public abstract R getInstance(T t);
}
