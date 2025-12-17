package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvidenceException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FailFastStarvationHandler<I extends AutoCloseable> implements PythonInterpreterPoolStarvationHandler<I>{
    @Override
    public I handle(PythonInterpreterFactory<I> interpreterFactory, BlockingQueue<I> pool, AtomicInteger poolSize) {
        throw new PythonInterpreterProvidenceException("Pool starvation detected. Pool size: " + poolSize + ".");
    }
}
