package com.vk.dwzkf.processors.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Processor<Target> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void handle(Target target) {
        if (validateTarget(target)) {
            logger.info("Validate passed for target:{}", target);
            processTarget(target);
            logger.info("Target processed completed for target:{}", target);
            return;
        }
        logger.warn("Validate failed for target:{}", target);
    }

    protected abstract boolean validateTarget(Target target);

    protected abstract void processTarget(Target target);
}
