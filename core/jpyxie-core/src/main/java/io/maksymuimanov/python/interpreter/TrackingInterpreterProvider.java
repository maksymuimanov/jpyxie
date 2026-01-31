package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
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
        if (this.closed.get()) {
            log.warn("Attempted to acquire interpreter from closed tracking provider");
            throw new PythonInterpreterProvisionException("Interpreter is closed");
        }
        try {
            log.debug("Creating new tracked interpreter, total tracked: [{}]", interpreterQueue.size() + 1);
            I interpreter = interpreterFactory.create();
            interpreterQueue.add(interpreter);
            return interpreter;
        } catch (Exception e) {
            log.error("Failed to create tracked interpreter", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (!this.closed.compareAndSet(false, true)) {
            log.debug("Tracking provider is already closed");
            return;
        }
        try {
            int size = interpreterQueue.size();
            log.info("Closing [{}] tracked interpreters", size);
            I current;
            while ((current = this.interpreterQueue.poll()) != null) {
                current.close();
            }
            log.info("Successfully closed [{}] tracked interpreters", size);
        } catch (Exception e) {
            log.error("Failed to close tracked interpreters", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
