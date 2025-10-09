package io.maksymuimanov.python.exception;

public class AopPythonExceptionException extends PythonException {
    public AopPythonExceptionException() {
    }

    public AopPythonExceptionException(String message) {
        super(message);
    }

    public AopPythonExceptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AopPythonExceptionException(Throwable cause) {
        super(cause);
    }

    public AopPythonExceptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

