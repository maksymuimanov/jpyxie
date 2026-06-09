package io.jpyxie.python.interpreter;

public class PoolPythonInterpreterProviderException extends PythonInterpreterProviderException {
    public static PoolPythonInterpreterProviderException poolStarvation() {
        return new PoolPythonInterpreterProviderException("Pool starvation detected");
    }

    public static PoolPythonInterpreterProviderException interrupted(InterruptedException exception) {
        return new PoolPythonInterpreterProviderException("Pool interrupted", exception);
    }

    public static PoolPythonInterpreterProviderException failedToAcquireInterpreter(Throwable throwable) {
        return new PoolPythonInterpreterProviderException("Failed to acquire interpreter from pool", throwable);
    }

    public static PoolPythonInterpreterProviderException failedToCreateInterpreter() {
        return new PoolPythonInterpreterProviderException("Failed to create interpreter during pool expansion");
    }

    public static PoolPythonInterpreterProviderException failedToFill(Throwable throwable) {
        return new PoolPythonInterpreterProviderException("Failed to fill pool", throwable);
    }

    public static PoolPythonInterpreterProviderException failedToCloseInterpreter(Throwable throwable) {
        return new PoolPythonInterpreterProviderException("Failed to close interpreter", throwable);
    }

    public static PoolPythonInterpreterProviderException failedToReturnInterpreter() {
        return new PoolPythonInterpreterProviderException("Failed to return interpreter to pool");
    }

    public PoolPythonInterpreterProviderException() {
    }

    public PoolPythonInterpreterProviderException(String message) {
        super(message);
    }

    public PoolPythonInterpreterProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolPythonInterpreterProviderException(Throwable cause) {
        super(cause);
    }

    public PoolPythonInterpreterProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
