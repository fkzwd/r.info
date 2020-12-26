package com.vk.dwzkf.magic_factory.test_environment.factory;

import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject;
import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactoryHolder {
    @Autowired
    private SimpleFactory simpleFactory;

    public Factory getFactory(TransferObject transferObject) {
        if (transferObject.getClass() == TransferObject1.class) {
            return simpleFactory;
        }
        throw new IllegalArgumentException();
    }
}
