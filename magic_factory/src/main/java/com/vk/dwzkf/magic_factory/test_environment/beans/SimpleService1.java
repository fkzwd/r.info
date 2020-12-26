package com.vk.dwzkf.magic_factory.test_environment.beans;

import com.vk.dwzkf.magic_factory.test_environment.type2.ExecutableObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleService1 {
    public void doWork(ExecutableObject executableObject) {
        log.info("[EXECUTING]: {}",executableObject);
    }
}
