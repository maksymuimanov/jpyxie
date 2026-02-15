package io.jpyxie.python.interpreter;

import io.jpyxie.python.exception.PythonInterpreterProvisionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
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
                int newSize = oldSize * sizeMultiplier;
                poolSize.set(newSize);
                log.info("Expanding pool from [{}] to [{}] interpreters", oldSize, newSize);
                for (int i = 0; i < newSize - oldSize; i++) {
                    pool.put(interpreterFactory.create());
                }
                log.debug("Created [{}] additional interpreters during expansion", newSize - oldSize);
            }
            I interpreter = pool.take();
            log.debug("Acquired interpreter after pool expansion");
            return interpreter;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted while waiting for interpreter during pool expansion");
            throw new PythonInterpreterProvisionException(e);
        } catch (Exception e) {
            log.error("Failed to expand interpreter pool", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
