package io.jpyxie.python.executor;

import io.jpyxie.python.PythonException;

public class PythonExecutionException extends PythonException {
    public PythonExecutionException() {
    }

    public PythonExecutionException(String message) {
        super(message);
    }

    public PythonExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonExecutionException(Throwable cause) {
        super(cause);
    }

    public PythonExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}