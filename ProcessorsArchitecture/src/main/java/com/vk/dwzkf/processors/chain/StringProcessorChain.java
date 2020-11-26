package com.vk.dwzkf.processors.chain;

import com.vk.dwzkf.processors.processor.markers.StringMarkerProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringProcessorChain extends ProcessorChain<StringMarkerProcessor, String> {
    public StringProcessorChain(List<StringMarkerProcessor> processors) {
        super(processors);
    }
}
