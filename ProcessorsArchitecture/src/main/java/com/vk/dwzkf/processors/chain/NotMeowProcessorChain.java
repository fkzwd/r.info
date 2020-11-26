package com.vk.dwzkf.processors.chain;

import com.vk.dwzkf.processors.processor.markers.NotMeowMarkerProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotMeowProcessorChain extends ProcessorChain<NotMeowMarkerProcessor, String> {
    public NotMeowProcessorChain(List<NotMeowMarkerProcessor> processors) {
        super(processors);
    }
}
