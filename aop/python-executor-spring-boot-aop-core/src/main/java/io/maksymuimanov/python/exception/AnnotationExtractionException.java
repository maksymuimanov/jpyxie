package io.maksymuimanov.python.exception;

import io.maksymuimanov.python.aspect.MultiPythonScriptExtractor;
import io.maksymuimanov.python.aspect.PythonAnnotationValueExtractor;
import io.maksymuimanov.python.aspect.SinglePythonScriptExtractor;

/**
 * Runtime exception thrown when extraction of values from annotations fails.
 * <p>
 * This exception indicates that an error occurred during reflection or
 * processing of annotation values, such as missing methods or invocation issues.
 * <p>
 * Typically thrown by implementations of {@link PythonAnnotationValueExtractor}
 * when they cannot properly extract annotation values.
 * <p>
 * This exception is unchecked to allow propagation without mandatory handling,
 * signaling a programming or configuration error.
 *
 * @see SinglePythonScriptExtractor
 * @see MultiPythonScriptExtractor
 * @since 1.0.0
 * @author w4t3rcs
 */
public class AnnotationExtractionException extends AopPythonExceptionException {
    public AnnotationExtractionException() {
    }

    public AnnotationExtractionException(String message) {
        super(message);
    }

    public AnnotationExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationExtractionException(Throwable cause) {
        super(cause);
    }

    public AnnotationExtractionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

