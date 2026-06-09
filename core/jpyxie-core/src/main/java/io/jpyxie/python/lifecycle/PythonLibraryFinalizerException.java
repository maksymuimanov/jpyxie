package io.jpyxie.python.lifecycle;

public class PythonLibraryFinalizerException extends PythonLifecycleException {
    public static PythonLibraryFinalizerException failedToFinalize(Throwable throwable) {
        return new PythonLibraryFinalizerException("Failed to uninstall Python libraries", throwable);
    }

    public PythonLibraryFinalizerException() {
        super();
    }

    public PythonLibraryFinalizerException(String message) {
        super(message);
    }

    public PythonLibraryFinalizerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonLibraryFinalizerException(Throwable cause) {
        super(cause);
    }

    public PythonLibraryFinalizerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
