package io.maksymuimanov.python.exception;

import io.maksymuimanov.python.executor.BasicPythonProcessStarter;

/**
 * Exception thrown when an error occurs while starting a process.
 * <p>
 * This exception typically indicates issues such as invalid commands,
 * insufficient permissions, or I/O errors that prevent the creation
 * of a new process.
 * <p>
 * It extends {@link PythonProcessExecutionException} and signals a failure that
 * usually requires corrective action in configuration or environment.
 *
 * @see BasicPythonProcessStarter
 * @since 1.0.0
 * @author w4t3rcs
 */
public class PythonProcessStartException extends PythonProcessExecutionException {
    public PythonProcessStartException() {
    }

    public PythonProcessStartException(String message) {
        super(message);
    }

    public PythonProcessStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonProcessStartException(Throwable cause) {
        super(cause);
    }

    public PythonProcessStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
