package io.maksymuimanov.python.exception;

public class PythonLibraryManagementException extends PythonException {
    public PythonLibraryManagementException() {
    }

    public PythonLibraryManagementException(String message) {
        super(message);
    }

    public PythonLibraryManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonLibraryManagementException(Throwable cause) {
        super(cause);
    }

    public PythonLibraryManagementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}