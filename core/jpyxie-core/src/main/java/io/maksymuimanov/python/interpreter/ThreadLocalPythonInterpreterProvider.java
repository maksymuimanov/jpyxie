package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Getter(AccessLevel.PROTECTED)
public class ThreadLocalPythonInterpreterProvider<I extends AutoCloseable> implements PythonInterpreterProvider<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final ThreadLocal<@Nullable I> threadLocal;
    private final Queue<I> interpreterQueue;

    public ThreadLocalPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this(interpreterFactory, new ThreadLocal<>(), new ConcurrentLinkedQueue<>());
    }

    public ThreadLocalPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, ThreadLocal<@Nullable I> threadLocal, Queue<I> interpreterQueue) {
        this.interpreterFactory = interpreterFactory;
        this.threadLocal = threadLocal;
        this.interpreterQueue = interpreterQueue;
    }

    @Override
    public I acquire() {
        try {
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
            log.error("Failed to acquire thread-local interpreter", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            int size = interpreterQueue.size();
            log.info("Closing [{}] thread-local interpreters", size);
            for (I interpreter : interpreterQueue) {
                interpreter.close();
            }
            log.info("Successfully closed [{}] thread-local interpreters", size);
        } catch (Exception e) {
            log.error("Failed to close thread-local interpreters", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
