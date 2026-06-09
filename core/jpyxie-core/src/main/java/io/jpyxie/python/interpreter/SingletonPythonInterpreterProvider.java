package io.jpyxie.python.interpreter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Getter(AccessLevel.PROTECTED)
public class SingletonPythonInterpreterProvider<I extends AutoCloseable> implements PythonInterpreterProvider<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final AtomicBoolean closed;
    @Nullable
    private volatile I interpreter;

    public SingletonPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this.interpreterFactory = interpreterFactory;
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public I acquire() {
        try {
            if (this.closed.get()) {
                throw PythonInterpreterProviderException.closed();
            }

            if (this.interpreter == null) {
                synchronized (SingletonPythonInterpreterProvider.class) {
                    if (interpreter == null) {
                        log.debug("Creating new singleton interpreter instance");
                        this.interpreter = this.interpreterFactory.create();
                        log.info("Singleton interpreter initialized successfully");
                    }
                }
            }

            return this.interpreter;
        } catch (Exception e) {
            throw PythonInterpreterProviderException.failedToAcquireInterpreter(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (!this.closed.compareAndSet(false, true)) {
                log.debug("Singleton interpreter is already closed");
                return;
            }

            if (this.interpreter != null) {
                log.info("Closing singleton interpreter instance");
                this.interpreter.close();
            }
        } catch (Exception e) {
            throw PythonInterpreterProviderException.failedToClose(e);
        }
    }
}
