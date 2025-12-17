package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvidenceException;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class ExpansionStarvationHandler<I extends AutoCloseable> implements PythonInterpreterPoolStarvationHandler<I>{
    private final int sizeMultiplier;

    @Override
    public I handle(PythonInterpreterFactory<I> interpreterFactory, BlockingQueue<I> pool, AtomicInteger poolSize) {
        try {
            synchronized (pool) {
                int oldSize = poolSize.get();
                poolSize.set(oldSize * sizeMultiplier);
                for (int i = 1; i < sizeMultiplier; i++) {
                    for (int j = 0; j < oldSize; j++) {
                        pool.put(interpreterFactory.create());
                    }
                }
            }
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PythonInterpreterProvidenceException(e);
        }
    }
}
