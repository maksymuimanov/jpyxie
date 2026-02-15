package io.maksymuimanov.python.exception;

public class PythonSerializationException extends PythonException {
    public PythonSerializationException() {
    }

    public PythonSerializationException(String message) {
        super(message);
    }

    public PythonSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonSerializationException(Throwable cause) {
        super(cause);
    }

    public PythonSerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}