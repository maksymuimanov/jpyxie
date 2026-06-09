package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.lifecycle.PythonLifecycleException;

public class SpringEventPythonLifecycleException extends PythonLifecycleException {
    public static SpringEventPythonLifecycleException failedToInitialize(Throwable throwable) {
        return new SpringEventPythonLifecycleException("Failed to initialize", throwable);
    }

    public static SpringEventPythonLifecycleException failedToFinish(Throwable throwable) {
        return new SpringEventPythonLifecycleException("Failed to finish", throwable);
    }

    public SpringEventPythonLifecycleException() {
        super();
    }

    public SpringEventPythonLifecycleException(String message) {
        super(message);
    }

    public SpringEventPythonLifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringEventPythonLifecycleException(Throwable cause) {
        super(cause);
    }

    public SpringEventPythonLifecycleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
