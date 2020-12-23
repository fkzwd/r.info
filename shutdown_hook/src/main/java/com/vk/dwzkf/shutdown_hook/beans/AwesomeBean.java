package com.vk.dwzkf.shutdown_hook.beans;

import com.vk.dwzkf.shutdown_hook.annotation.ShutdownHookMethod;
import org.springframework.stereotype.Component;

@Component
public class AwesomeBean {
    public void someGreatPayload(Object someArg) {
        //do very hard job
        System.out.println("[INFO] VERY HARD WORK DONE!!");
        return;
    }

    @ShutdownHookMethod
    public void onShutdown() {
        System.out.println("[SHUTDOWN HOOK] Application closed. First[1] hook: But \"Hello, World!\"");
    }

    @ShutdownHookMethod
    public void onShutdown2() {
        System.out.println("[SHUTDOWN HOOK] Hello! Im second[2] hook!");
    }

    @ShutdownHookMethod
    public void onShutdown3() {
        System.out.println("[SHUTDOWN HOOK] Hello! Im third[3] hook!");
    }
}
