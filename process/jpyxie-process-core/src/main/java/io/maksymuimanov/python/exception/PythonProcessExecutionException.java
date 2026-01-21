package io.maksymuimanov.python.exception;

public class PythonProcessExecutionException extends PythonExecutionException {
    public PythonProcessExecutionException() {
    }

    public PythonProcessExecutionException(String message) {
        super(message);
    }

    public PythonProcessExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonProcessExecutionException(Throwable cause) {
        super(cause);
    }

    public PythonProcessExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
