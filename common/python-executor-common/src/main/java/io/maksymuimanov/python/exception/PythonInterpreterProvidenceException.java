package io.maksymuimanov.python.exception;

public class PythonInterpreterProvidenceException extends PythonException {
    public PythonInterpreterProvidenceException() {
    }

    public PythonInterpreterProvidenceException(String message) {
        super(message);
    }

    public PythonInterpreterProvidenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonInterpreterProvidenceException(Throwable cause) {
        super(cause);
    }

    public PythonInterpreterProvidenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
