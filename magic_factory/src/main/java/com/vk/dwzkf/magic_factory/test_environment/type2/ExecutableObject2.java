package com.vk.dwzkf.magic_factory.test_environment.type2;

import com.vk.dwzkf.magic_factory.annotation.Transformable;
import com.vk.dwzkf.magic_factory.test_environment.beans.MagicService2;
import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@RequiredArgsConstructor
@Getter
@Setter
@Component
@Transformable(TransferObject2.class)
public class ExecutableObject2 extends ExecutableObject{
    private final MagicService2 magicService2;
    private String message;
    private String ignoredString;

    @Override
    public void run() {
        magicService2.doMagicWork(this);
    }
}
