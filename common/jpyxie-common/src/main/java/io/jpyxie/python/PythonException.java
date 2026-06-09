package io.jpyxie.python;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PythonException extends RuntimeException {
    public PythonException() {
        log.error(this.getMessage(), this);
    }

    public PythonException(String message) {
        super(message);
        log.error(this.getMessage(), this);
    }

    public PythonException(String message, Throwable cause) {
        super(message, cause);
        log.error(this.getMessage(), this);
    }

    public PythonException(Throwable cause) {
        super(cause);
        log.error(this.getMessage(), this);
    }

    public PythonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        log.error(this.getMessage(), this);
    }
}
