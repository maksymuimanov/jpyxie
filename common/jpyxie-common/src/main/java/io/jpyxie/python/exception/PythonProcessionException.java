package io.jpyxie.python.exception;

public class PythonProcessionException extends PythonException {
    public PythonProcessionException() {
    }

    public PythonProcessionException(String message) {
        super(message);
    }

    public PythonProcessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonProcessionException(Throwable cause) {
        super(cause);
    }

    public PythonProcessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
