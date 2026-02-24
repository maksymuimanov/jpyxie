package io.jpyxie.python.exception;

public class PythonEnvironmentException extends PythonFileException {
    public PythonEnvironmentException() {
    }

    public PythonEnvironmentException(String message) {
        super(message);
    }

    public PythonEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonEnvironmentException(Throwable cause) {
        super(cause);
    }

    public PythonEnvironmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
