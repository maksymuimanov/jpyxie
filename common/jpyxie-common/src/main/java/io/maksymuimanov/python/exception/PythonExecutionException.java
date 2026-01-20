package io.maksymuimanov.python.exception;

import io.maksymuimanov.python.executor.PythonExecutor;

/**
 * Exception thrown when a Python script execution process is interrupted.
 * <p>
 * This typically occurs when a thread running the Python script is interrupted,
 * or when the execution process is forcibly terminated.
 * <p>
 * This exception extends {@link PythonException} and signals
 * an abnormal termination of the script execution flow.
 *
 * @see PythonExecutor
 * @since 1.0.0
 * @author w4t3rcs
 */
public class PythonExecutionException extends PythonException {
    public PythonExecutionException() {
    }

    public PythonExecutionException(String message) {
        super(message);
    }

    public PythonExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonExecutionException(Throwable cause) {
        super(cause);
    }

    public PythonExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}