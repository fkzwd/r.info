package com.vk.dwzkf.magic_factory.test_environment.type2;

import com.vk.dwzkf.magic_factory.test_environment.beans.SimpleService1;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
@Setter
@Component
@Scope
@ToString
public class ExecutableObject1 extends ExecutableObject {
    private final SimpleService1 simpleService1;

    private int id;
    private String command;

    @Override
    public void run() {
        simpleService1.doWork(this);
    }
}
