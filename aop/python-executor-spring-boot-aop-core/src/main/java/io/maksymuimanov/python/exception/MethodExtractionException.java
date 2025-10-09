package io.maksymuimanov.python.exception;

public class MethodExtractionException extends AopPythonExceptionException {
    public MethodExtractionException() {
    }

    public MethodExtractionException(String message) {
        super(message);
    }

    public MethodExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodExtractionException(Throwable cause) {
        super(cause);
    }

    public MethodExtractionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

