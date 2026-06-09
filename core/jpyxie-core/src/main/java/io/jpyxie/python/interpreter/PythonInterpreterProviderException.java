package io.jpyxie.python.interpreter;

public class PythonInterpreterProviderException extends PythonInterpreterException {
    public static PythonInterpreterProviderException closed() {
        return new PythonInterpreterProviderException("Python interpreter provider is closed");
    }

    public static PythonInterpreterProviderException failedToAcquireInterpreter(Throwable throwable) {
        return new PythonInterpreterProviderException("Failed to acquire interpreter", throwable);
    }

    public static PythonInterpreterProviderException failedToReleaseInterpreter(Throwable throwable) {
        return new PythonInterpreterProviderException("Failed to release interpreter", throwable);
    }

    public static PythonInterpreterProviderException failedToClose(Throwable throwable) {
        return new PythonInterpreterProviderException("Failed to close", throwable);
    }

    public PythonInterpreterProviderException() {
    }

    public PythonInterpreterProviderException(String message) {
        super(message);
    }

    public PythonInterpreterProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonInterpreterProviderException(Throwable cause) {
        super(cause);
    }

    public PythonInterpreterProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
