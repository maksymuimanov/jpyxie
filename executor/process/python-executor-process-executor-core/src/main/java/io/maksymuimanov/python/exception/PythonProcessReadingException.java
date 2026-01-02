package io.maksymuimanov.python.exception;

import io.maksymuimanov.python.error.BasicPythonErrorProcessHandler;
import io.maksymuimanov.python.output.BasicPythonOutputProcessHandler;

/**
 * Exception thrown when an error occurs while reading output from a Python process.
 * <p>
 * This exception typically indicates I/O errors encountered when reading
 * from the standard output or error streams of a Python process.
 * <p>
 * It extends {@link PythonProcessExecutionException} and signals a failure in
 * communication or data retrieval from the Python execution environment.
 *
 * @see BasicPythonOutputProcessHandler
 * @see BasicPythonErrorProcessHandler
 * @since 1.0.0
 * @author w4t3rcs
 */
public class PythonProcessReadingException extends PythonProcessExecutionException {
    public PythonProcessReadingException() {
    }

    public PythonProcessReadingException(String message) {
        super(message);
    }

    public PythonProcessReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonProcessReadingException(Throwable cause) {
        super(cause);
    }

    public PythonProcessReadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
