package io.maksymuimanov.python.exception;

public class PythonProcessDeserializationException extends PythonProcessExecutionException {
    public PythonProcessDeserializationException() {
    }

    public PythonProcessDeserializationException(String message) {
        super(message);
    }

    public PythonProcessDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonProcessDeserializationException(Throwable cause) {
        super(cause);
    }

    public PythonProcessDeserializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
