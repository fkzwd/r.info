package com.vk.dwzkf.processors.processor.impl;

import com.vk.dwzkf.processors.processor.markers.NotMeowMarkerProcessor;
import org.springframework.stereotype.Component;

@Component
public class NoSpacesProcessor extends NotMeowMarkerProcessor {
    @Override
    protected boolean validateTarget(String s) {
        return s != null;
    }

    @Override
    protected void processTarget(String s) {
        logger.info("'{}' with no spaces = '{}'",
                s,
                s.replaceAll(" ", "")
        );
    }
}
