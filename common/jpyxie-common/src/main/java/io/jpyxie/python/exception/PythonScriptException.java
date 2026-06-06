package io.jpyxie.python.exception;

import java.io.IOException;

public class PythonScriptException extends PythonException {
    public static PythonScriptException invalidFileFormatException() {
        return new PythonScriptException("Invalid file format. The valid format is .py");
    }

    public static PythonScriptException inputStreamException(IOException exception) {
        return new PythonScriptException("Something wrong with reading from reading InputStream", exception);
    }

    public PythonScriptException() {
    }

    public PythonScriptException(String message) {
        super(message);
    }

    public PythonScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonScriptException(Throwable cause) {
        super(cause);
    }

    public PythonScriptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
