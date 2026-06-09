package io.jpyxie.python.common;

import io.jpyxie.python.PythonException;

public class PythonFileException extends PythonException {
    public static PythonFileException projectDirectoryIsNotUserDirectoryChild(String directory) {
        return new PythonFileException("Project directory is not a child of user directory: " + directory);
    }

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
