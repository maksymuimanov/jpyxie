package io.jpyxie.python.exception;

public class PythonTypeConversionException extends PythonException {
    public PythonTypeConversionException() {
    }

    public PythonTypeConversionException(String message) {
        super(message);
    }

    public PythonTypeConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonTypeConversionException(Throwable cause) {
        super(cause);
    }

    public PythonTypeConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}