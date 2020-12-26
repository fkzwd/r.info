package com.vk.dwzkf.magic_factory.test_environment.type2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class ExecutableObject implements Runnable{
    private String name;
}
