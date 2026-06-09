package io.jpyxie.python.interpreter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Getter(AccessLevel.PROTECTED)
public class ThreadLocalPythonInterpreterProvider<I extends AutoCloseable> implements PythonInterpreterProvider<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final ThreadLocal<@Nullable I> threadLocal;
    private final Queue<I> interpreterQueue;
    private final AtomicBoolean closed;

    public ThreadLocalPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this(interpreterFactory, new ConcurrentLinkedQueue<>());
    }

    public ThreadLocalPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, Queue<I> interpreterQueue) {
        this(interpreterFactory, new ThreadLocal<>(), interpreterQueue);
    }

    public ThreadLocalPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, ThreadLocal<@Nullable I> threadLocal, Queue<I> interpreterQueue) {
        this.interpreterFactory = interpreterFactory;
        this.threadLocal = threadLocal;
        this.interpreterQueue = interpreterQueue;
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public I acquire() {
        try {
            if (this.closed.get()) {
                throw PythonInterpreterProviderException.closed();
            }

            I threadLocalInterpreter = threadLocal.get();
            String threadName = Thread.currentThread().getName();
            if (threadLocalInterpreter == null) {
                log.debug("Creating new thread-local interpreter for thread [{}]", threadName);
                I interpreter = this.interpreterFactory.create();
                threadLocal.set(interpreter);
                interpreterQueue.offer(interpreter);
                log.debug("Created and cached thread-local interpreter");
                return interpreter;
            }
            log.debug("Reusing existing thread-local interpreter for thread [{}]", threadName);
            return threadLocalInterpreter;
        } catch (Exception e) {
            throw PythonInterpreterProviderException.failedToAcquireInterpreter(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (!this.closed.compareAndSet(false, true)) {
                log.debug("Thread-local provider is already closed");
                return;
            }

            int size = interpreterQueue.size();
            log.info("Closing [{}] thread-local interpreters", size);
            for (I interpreter : interpreterQueue) {
                interpreter.close();
            }
            log.info("Successfully closed [{}] thread-local interpreters", size);
        } catch (Exception e) {
            throw PythonInterpreterProviderException.failedToClose(e);
        }
    }
}
