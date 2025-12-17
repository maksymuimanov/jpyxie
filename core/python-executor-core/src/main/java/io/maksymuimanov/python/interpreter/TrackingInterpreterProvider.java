package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvidenceException;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter(AccessLevel.PROTECTED)
public class TrackingInterpreterProvider<I extends AutoCloseable> implements PythonInterpreterProvider<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final Queue<I> interpreterQueue;
    private final AtomicBoolean closed;

    public TrackingInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this(interpreterFactory, new ConcurrentLinkedQueue<>());
    }

    public TrackingInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, Queue<I> interpreterQueue) {
        this.interpreterFactory = interpreterFactory;
        this.interpreterQueue = interpreterQueue;
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public I acquire() {
        if (this.closed.get()) throw new PythonInterpreterProvidenceException("Interpreter is closed");
        try {
            I interpreter = interpreterFactory.create();
            interpreterQueue.add(interpreter);
            return interpreter;
        } catch (Exception e) {
            throw new PythonInterpreterProvidenceException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (!this.closed.compareAndSet(false, true)) return;
        I current;
        while ((current = this.interpreterQueue.poll()) != null) {
            current.close();
        }
    }
}
