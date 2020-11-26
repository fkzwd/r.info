package com.vk.dwzkf.processors.processor.impl;

import com.vk.dwzkf.processors.processor.markers.NotMeowMarkerProcessor;
import org.springframework.stereotype.Component;

@Component
public class StringProcessor extends NotMeowMarkerProcessor {
    @Override
    protected boolean validateTarget(String s) {
        return s != null;
    }

    @Override
    protected void processTarget(String s) {
        String processed = s.toUpperCase();
        logger.info("Some process for string. String={}", processed);
    }
}
