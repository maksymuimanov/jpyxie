package io.jpyxie.python.exception;

public class PythonInterpreterProvisionException extends PythonException {
    public PythonInterpreterProvisionException() {
    }

    public PythonInterpreterProvisionException(String message) {
        super(message);
    }

    public PythonInterpreterProvisionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonInterpreterProvisionException(Throwable cause) {
        super(cause);
    }

    public PythonInterpreterProvisionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
