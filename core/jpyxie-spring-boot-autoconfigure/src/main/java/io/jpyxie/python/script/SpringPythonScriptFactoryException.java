package io.jpyxie.python.script;

import io.jpyxie.python.common.PythonFileException;

public class SpringPythonScriptFactoryException extends PythonFileException {
    public static SpringPythonScriptFactoryException emptyPath() {
        return new SpringPythonScriptFactoryException("Path cannot be empty");
    }

    public static SpringPythonScriptFactoryException notFound(CharSequence path) {
        return new SpringPythonScriptFactoryException(path + " not found");
    }

    public static SpringPythonScriptFactoryException failedToOpen(String name, CharSequence path, Throwable throwable) {
        return new SpringPythonScriptFactoryException("Failed to open file: [" + name + ": " + path + "]", throwable);
    }

    public SpringPythonScriptFactoryException() {
        super();
    }

    public SpringPythonScriptFactoryException(String message) {
        super(message);
    }

    public SpringPythonScriptFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringPythonScriptFactoryException(Throwable cause) {
        super(cause);
    }

    public SpringPythonScriptFactoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
