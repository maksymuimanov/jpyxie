package io.maksymuimanov.python.exception;

public class PythonDeserializationException extends PythonException {
    public PythonDeserializationException() {
    }

    public PythonDeserializationException(String message) {
        super(message);
    }

    public PythonDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonDeserializationException(Throwable cause) {
        super(cause);
    }

    public PythonDeserializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}