package com.vk.dwzkf.magic_factory.test_environment.type1;

import com.vk.dwzkf.magic_factory.annotation.FactoryIgnore;
import com.vk.dwzkf.magic_factory.annotation.Transformable;
import com.vk.dwzkf.magic_factory.test_environment.type2.ExecutableObject2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferObject2 extends TransferObject {
    private String message;
    @FactoryIgnore
    private String ignoredString;
}
