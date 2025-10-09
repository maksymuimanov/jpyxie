package io.maksymuimanov.python.exception;

import io.maksymuimanov.python.executor.PythonExecutor;

/**
 * Exception thrown when a Python script execution process is interrupted.
 * <p>
 * This typically occurs when a thread running the Python script is interrupted,
 * or when the execution process is forcibly terminated.
 * <p>
 * This exception extends {@link RuntimeException} and signals
 * an abnormal termination of the script execution flow.
 *
 * @see PythonExecutor
 * @since 1.0.0
 * @author w4t3rcs
 */
public class PythonScriptExecutionException extends RuntimeException {
    /**
     * Constructs a new {@code PythonScriptExecutionException} with the specified message.
     *
     * @param message the message (non-null)
     */
    public PythonScriptExecutionException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code PythonScriptExecutionException} with the specified cause.
     *
     * @param cause the underlying cause of the interruption (non-null)
     */
    public PythonScriptExecutionException(Throwable cause) {
        super(cause);
    }
}