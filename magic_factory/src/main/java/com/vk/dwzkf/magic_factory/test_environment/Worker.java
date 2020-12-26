package com.vk.dwzkf.magic_factory.test_environment;

import com.vk.dwzkf.magic_factory.factory.MagicFactory;
import com.vk.dwzkf.magic_factory.test_environment.factory.FactoryHolder;
import com.vk.dwzkf.magic_factory.test_environment.factory.SimpleFactory;
import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject;
import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject1;
import com.vk.dwzkf.magic_factory.test_environment.type2.ExecutableObject;
import jdk.jfr.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class Worker {
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final FactoryHolder factoryHolder;
    private final MagicFactory magicFactory;

    public void doWork(TransferObject transferObject) {
        ExecutableObject executable = factoryHolder.getFactory(transferObject)
                .getInstance(transferObject);
        executorService.execute(executable);
    }

    public void doMagicWork(TransferObject transferObject) {
        ExecutableObject executableObject = magicFactory.getInstance(transferObject);
        executorService.execute(executableObject);
    }
}
