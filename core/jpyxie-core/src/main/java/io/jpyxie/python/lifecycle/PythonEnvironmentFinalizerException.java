package io.jpyxie.python.lifecycle;

public class PythonEnvironmentFinalizerException extends PythonLifecycleException {
    public static PythonEnvironmentFinalizerException failedToFinalize(Throwable throwable) {
        return new PythonEnvironmentFinalizerException("Failed to finalize Python environment", throwable);
    }

    public PythonEnvironmentFinalizerException() {
        super();
    }

    public PythonEnvironmentFinalizerException(String message) {
        super(message);
    }

    public PythonEnvironmentFinalizerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonEnvironmentFinalizerException(Throwable cause) {
        super(cause);
    }

    public PythonEnvironmentFinalizerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
