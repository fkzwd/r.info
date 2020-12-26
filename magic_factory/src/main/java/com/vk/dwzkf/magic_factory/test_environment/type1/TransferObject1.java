package com.vk.dwzkf.magic_factory.test_environment.type1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferObject1 extends TransferObject{
    private int id;
    private String command;
}
