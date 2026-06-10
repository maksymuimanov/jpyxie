package io.jpyxie.python.bind;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GrpcPythonDeserializerException extends PythonDeserializationException {
    public static GrpcPythonDeserializerException failedToReadJson(JsonProcessingException exception) {
        return new GrpcPythonDeserializerException("Failed to read JSON", exception);
    }

    public GrpcPythonDeserializerException() {
        super();
    }

    public GrpcPythonDeserializerException(String message) {
        super(message);
    }

    public GrpcPythonDeserializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrpcPythonDeserializerException(Throwable cause) {
        super(cause);
    }

    public GrpcPythonDeserializerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
