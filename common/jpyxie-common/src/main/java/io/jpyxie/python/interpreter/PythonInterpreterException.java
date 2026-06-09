package io.jpyxie.python.interpreter;

import io.jpyxie.python.PythonException;

public class PythonInterpreterException extends PythonException {
    public PythonInterpreterException() {
    }

    public PythonInterpreterException(String message) {
        super(message);
    }

    public PythonInterpreterException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonInterpreterException(Throwable cause) {
        super(cause);
    }

    public PythonInterpreterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
