package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class ExpansionStarvationHandler<I extends AutoCloseable> implements PythonInterpreterPoolStarvationHandler<I>{
    public static final int DEFAULT_SIZE_MULTIPLIER = 2;
    private final int sizeMultiplier;

    public ExpansionStarvationHandler() {
        this(DEFAULT_SIZE_MULTIPLIER);
    }

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
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
