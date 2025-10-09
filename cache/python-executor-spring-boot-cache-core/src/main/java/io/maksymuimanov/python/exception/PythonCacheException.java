package io.maksymuimanov.python.exception;

public class PythonCacheException extends PythonException {
    public PythonCacheException() {
        super();
    }

    public PythonCacheException(String message) {
        super(message);
    }

    public PythonCacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonCacheException(Throwable cause) {
        super(cause);
    }

    public PythonCacheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}