package io.jpyxie.python.exception;

import java.util.List;

public class PythonLibraryManagementException extends PythonException {
    protected static final String EXCEPTION_MESSAGE_FORMAT = "%s failed with exit code: %d";

    public PythonLibraryManagementException() {
    }

    public PythonLibraryManagementException(String command, int exitValue) {
        this(String.format(EXCEPTION_MESSAGE_FORMAT, command, exitValue));
    }

    public PythonLibraryManagementException(List<String> commandList, int exitValue) {
        this(String.format(EXCEPTION_MESSAGE_FORMAT, String.join(" ", commandList), exitValue));
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