package io.jpyxie.python.interpreter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@FunctionalInterface
public interface PythonInterpreterPoolStarvationHandler<I extends AutoCloseable> {
    I handle(PythonInterpreterFactory<I> interpreterFactory, BlockingQueue<I> pool, AtomicInteger poolSize);
}
