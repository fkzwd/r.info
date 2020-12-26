package com.vk.dwzkf.magic_factory.test_environment.factory;

import com.vk.dwzkf.magic_factory.test_environment.beans.SimpleService1;
import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject1;
import com.vk.dwzkf.magic_factory.test_environment.type2.ExecutableObject;
import com.vk.dwzkf.magic_factory.test_environment.type2.ExecutableObject1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleFactory extends Factory<TransferObject1, ExecutableObject1>{
    private final SimpleService1 simpleService1;

    public ExecutableObject1 getInstance(TransferObject1 transferObject1) {
        ExecutableObject1 executableObject1 = new ExecutableObject1(simpleService1);
        executableObject1.setId(transferObject1.getId());
        executableObject1.setCommand(transferObject1.getCommand());
        executableObject1.setName(transferObject1.getName());
        return executableObject1;
    }
}
