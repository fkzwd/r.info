package com.vk.dwzkf.magic_factory;

import com.vk.dwzkf.magic_factory.test_environment.Worker;
import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject;
import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject1;
import com.vk.dwzkf.magic_factory.test_environment.type1.TransferObject2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class MagicFactoryApplication {

    @Autowired
    @Lazy
    private Worker worker;

    public static void main(String[] args) {
        SpringApplication.run(MagicFactoryApplication.class, args)
                .getBean(MagicFactoryApplication.class)
                .run();
    }

    public void run() {
        TransferObject1 transferObject1 = new TransferObject1(11,"command1");
        transferObject1.setName("TransferObject1");
        worker.doWork(transferObject1);

        TransferObject2 transferObject2 = new TransferObject2("* * MAGIC MESSAGE * *",
                "Ignored param");
        worker.doMagicWork(transferObject2);
    }
}
