package com.vk.dwzkf.processors.chain;

import com.vk.dwzkf.processors.processor.Processor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class ProcessorChain<P extends Processor<T>, T> {
    protected final List<P> processors;

    public void handle(T target) {
        processors.forEach(p -> p.handle(target));
    }
}
