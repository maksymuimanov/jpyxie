package io.jpyxie.python.lifecycle;

public class PythonEnvironmentInitializerException extends PythonLifecycleException {
    public static PythonEnvironmentInitializerException failedToInitialize(Throwable throwable) {
        return new PythonEnvironmentInitializerException("Failed to initialize Python environment", throwable);
    }

    public PythonEnvironmentInitializerException() {
        super();
    }

    public PythonEnvironmentInitializerException(String message) {
        super(message);
    }

    public PythonEnvironmentInitializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonEnvironmentInitializerException(Throwable cause) {
        super(cause);
    }

    public PythonEnvironmentInitializerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
