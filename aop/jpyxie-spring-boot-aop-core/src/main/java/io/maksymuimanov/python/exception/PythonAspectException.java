package io.maksymuimanov.python.exception;

public class PythonAspectException extends PythonException {
    public PythonAspectException() {
    }

    public PythonAspectException(String message) {
        super(message);
    }

    public PythonAspectException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonAspectException(Throwable cause) {
        super(cause);
    }

    public PythonAspectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

