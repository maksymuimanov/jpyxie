package io.jpyxie.python.exception;

public class PythonFileException extends PythonException {
    public PythonFileException() {
    }

    public PythonFileException(String message) {
        super(message);
    }

    public PythonFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonFileException(Throwable cause) {
        super(cause);
    }

    public PythonFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
