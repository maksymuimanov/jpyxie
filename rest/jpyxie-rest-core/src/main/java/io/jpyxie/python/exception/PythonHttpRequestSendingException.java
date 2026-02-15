package io.jpyxie.python.exception;

public class PythonHttpRequestSendingException extends PythonException {
    public PythonHttpRequestSendingException() {
    }

    public PythonHttpRequestSendingException(String message) {
        super(message);
    }

    public PythonHttpRequestSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonHttpRequestSendingException(Throwable cause) {
        super(cause);
    }

    public PythonHttpRequestSendingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
