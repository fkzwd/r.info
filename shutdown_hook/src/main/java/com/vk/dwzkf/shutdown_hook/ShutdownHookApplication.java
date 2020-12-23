package com.vk.dwzkf.shutdown_hook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShutdownHookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShutdownHookApplication.class, args);
    }

}
