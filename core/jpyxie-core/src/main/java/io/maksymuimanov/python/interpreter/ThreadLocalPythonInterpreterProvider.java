package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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
            if (threadLocalInterpreter == null) {
                I interpreter = this.interpreterFactory.create();
                threadLocal.set(interpreter);
                interpreterQueue.offer(interpreter);
                return interpreter;
            }
            return threadLocalInterpreter;
        } catch (Exception e) {
            throw new PythonInterpreterProvisionException(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            for (I interpreter : interpreterQueue) {
                interpreter.close();
            }
        } catch (Exception e) {
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
