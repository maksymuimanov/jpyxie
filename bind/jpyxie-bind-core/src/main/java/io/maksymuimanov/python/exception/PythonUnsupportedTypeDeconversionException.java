package io.maksymuimanov.python.exception;

public class PythonUnsupportedTypeDeconversionException extends PythonException {
    public PythonUnsupportedTypeDeconversionException() {
    }

    public PythonUnsupportedTypeDeconversionException(String message) {
        super(message);
    }

    public PythonUnsupportedTypeDeconversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonUnsupportedTypeDeconversionException(Throwable cause) {
        super(cause);
    }

    public PythonUnsupportedTypeDeconversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
