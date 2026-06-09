package io.jpyxie.python.lifecycle;

public class PythonLibraryInitializerException extends PythonLifecycleException {
    public static PythonLibraryInitializerException failedToInitialize(Throwable throwable) {
        return new PythonLibraryInitializerException("Failed to install Python libraries", throwable);
    }

    public PythonLibraryInitializerException() {
        super();
    }

    public PythonLibraryInitializerException(String message) {
        super(message);
    }

    public PythonLibraryInitializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonLibraryInitializerException(Throwable cause) {
        super(cause);
    }

    public PythonLibraryInitializerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
