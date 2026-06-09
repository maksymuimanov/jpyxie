package io.jpyxie.python.processor;

import io.jpyxie.python.PythonException;
import io.jpyxie.python.script.PythonScript;

public class PythonProcessionException extends PythonException {
    public static PythonProcessionException failedToProcess(PythonScript script, Throwable throwable) {
        return new PythonProcessionException("Failed to process Python script: " + script.getName(), throwable);
    }

    public PythonProcessionException() {
    }

    public PythonProcessionException(String message) {
        super(message);
    }

    public PythonProcessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonProcessionException(Throwable cause) {
        super(cause);
    }

    public PythonProcessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
