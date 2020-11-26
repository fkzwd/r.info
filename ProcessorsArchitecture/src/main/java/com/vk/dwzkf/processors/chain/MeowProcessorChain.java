package com.vk.dwzkf.processors.chain;

import com.vk.dwzkf.processors.processor.markers.MeowMarkerProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeowProcessorChain extends ProcessorChain<MeowMarkerProcessor, String> {
    public MeowProcessorChain(List<MeowMarkerProcessor> processors) {
        super(processors);
    }
}
