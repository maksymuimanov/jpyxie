package io.maksymuimanov.python.exception;

public class PythonProcessFinishException extends PythonProcessExecutionException {
    public PythonProcessFinishException() {
    }

    public PythonProcessFinishException(String message) {
        super(message);
    }

    public PythonProcessFinishException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonProcessFinishException(Throwable cause) {
        super(cause);
    }

    public PythonProcessFinishException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
