package com.vk.dwzkf.processors.processor.impl;

import com.vk.dwzkf.processors.processor.markers.MeowMarkerProcessor;
import org.springframework.stereotype.Component;

@Component
public class MeowProcessor extends MeowMarkerProcessor {
    @Override
    protected boolean validateTarget(String s) {
        if (s==null) return false;
        return s.contains("meow");
    }

    @Override
    protected void processTarget(String s) {
        logger.info("I just received message '{}'. Meow to you too!", s);
    }
}
