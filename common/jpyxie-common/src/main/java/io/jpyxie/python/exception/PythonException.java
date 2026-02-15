package io.jpyxie.python.exception;

public class PythonException extends RuntimeException {
    public PythonException() {
    }

    public PythonException(String message) {
        super(message);
    }

    public PythonException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonException(Throwable cause) {
        super(cause);
    }

    public PythonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
