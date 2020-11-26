package com.vk.dwzkf.processors;

import com.vk.dwzkf.processors.chain.MeowProcessorChain;
import com.vk.dwzkf.processors.chain.NotMeowProcessorChain;
import com.vk.dwzkf.processors.chain.StringProcessorChain;
import com.vk.dwzkf.processors.processor.impl.MeowProcessor;
import com.vk.dwzkf.processors.processor.impl.StringProcessor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
@SpringBootApplication
public class ProcessorsApplication {
    private final StringProcessorChain stringProcessorChain;
    private final MeowProcessorChain meowProcessorChain;
    private final NotMeowProcessorChain notMeowProcessorChain;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ProcessorsApplication.class, args);
        ProcessorsApplication processorsApplication = applicationContext.getBean(ProcessorsApplication.class);
        System.out.println("* * * * *");
        System.out.println();
        processorsApplication.forAllStrings();
        System.out.println("* * * * *");
        System.out.println();
        processorsApplication.forMeow();
        System.out.println("* * * * *");
        System.out.println();
        processorsApplication.forNotMeow();
    }

    public void forAllStrings() {
        List.of("no me0w")
                .forEach(stringProcessorChain::handle);
    }

    public void forMeow() {
        List.of("meow", "m e o w")
                .forEach(meowProcessorChain::handle);
    }

    public void forNotMeow() {
        List.of("m e o w")
                .forEach(notMeowProcessorChain::handle);
    }
}
