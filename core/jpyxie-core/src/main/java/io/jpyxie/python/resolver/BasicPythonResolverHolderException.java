package io.jpyxie.python.resolver;

import io.jpyxie.python.script.PythonScript;
import io.jpyxie.python.script.PythonScriptException;

public class BasicPythonResolverHolderException extends PythonScriptException {
    public static BasicPythonResolverHolderException failedToResolve(PythonScript script, Throwable throwable) {
        return new BasicPythonResolverHolderException("Failed to resolve Python script [name: " + script.getName() + "]", throwable);
    }

    public BasicPythonResolverHolderException() {
        super();
    }

    public BasicPythonResolverHolderException(String message) {
        super(message);
    }

    public BasicPythonResolverHolderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicPythonResolverHolderException(Throwable cause) {
        super(cause);
    }

    public BasicPythonResolverHolderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
