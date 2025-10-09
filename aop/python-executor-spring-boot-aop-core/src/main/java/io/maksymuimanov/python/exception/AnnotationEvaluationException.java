package io.maksymuimanov.python.exception;

public class AnnotationEvaluationException extends AopPythonExceptionException {
    public AnnotationEvaluationException() {
    }

    public AnnotationEvaluationException(String message) {
        super(message);
    }

    public AnnotationEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationEvaluationException(Throwable cause) {
        super(cause);
    }

    public AnnotationEvaluationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

