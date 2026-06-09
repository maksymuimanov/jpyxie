package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonException;

public class PythonObjectDictionaryConverterException extends PythonException {
    public static PythonObjectDictionaryConverterException failedToGetDictionaryValue(Throwable throwable) {
        return new PythonObjectDictionaryConverterException("Failed to get dictionary value", throwable);
    }

    public PythonObjectDictionaryConverterException() {
    }

    public PythonObjectDictionaryConverterException(String message) {
        super(message);
    }

    public PythonObjectDictionaryConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonObjectDictionaryConverterException(Throwable cause) {
        super(cause);
    }

    public PythonObjectDictionaryConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}