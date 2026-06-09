package io.jpyxie.python.library;

import io.jpyxie.python.PythonException;

public class PythonLibraryException extends PythonException {
    public PythonLibraryException() {
    }

    public PythonLibraryException(String message) {
        super(message);
    }

    public PythonLibraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonLibraryException(Throwable cause) {
        super(cause);
    }

    public PythonLibraryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}