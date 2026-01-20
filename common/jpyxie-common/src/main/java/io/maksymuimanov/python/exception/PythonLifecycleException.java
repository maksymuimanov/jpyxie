package io.maksymuimanov.python.exception;

public class PythonLifecycleException extends PythonException {
    public PythonLifecycleException() {
    }

    public PythonLifecycleException(String message) {
        super(message);
    }

    public PythonLifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonLifecycleException(Throwable cause) {
        super(cause);
    }

    public PythonLifecycleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
