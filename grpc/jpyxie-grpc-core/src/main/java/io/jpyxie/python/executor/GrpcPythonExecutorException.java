package io.jpyxie.python.executor;

import io.jpyxie.python.script.PythonScript;

public class GrpcPythonExecutorException extends PythonExecutionException {
    public static GrpcPythonExecutorException failedToExecute(PythonScript script, Throwable throwable) {
        return new GrpcPythonExecutorException("Failed to execute Python script [" + script.getName() + "]", throwable);
    }

    public GrpcPythonExecutorException() {
        super();
    }

    public GrpcPythonExecutorException(String message) {
        super(message);
    }

    public GrpcPythonExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrpcPythonExecutorException(Throwable cause) {
        super(cause);
    }

    public GrpcPythonExecutorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
