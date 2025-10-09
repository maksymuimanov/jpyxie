package io.maksymuimanov.python.exception;

public class MethodParameterExtractionException extends MethodExtractionException {
    public MethodParameterExtractionException() {
    }

    public MethodParameterExtractionException(String message) {
        super(message);
    }

    public MethodParameterExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodParameterExtractionException(Throwable cause) {
        super(cause);
    }

    public MethodParameterExtractionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

