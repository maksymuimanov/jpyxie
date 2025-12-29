package io.maksymuimanov.python.resolver;

import io.maksymuimanov.python.script.PythonScript;

import java.util.Map;

public class PrettyResolver implements PythonResolver {
    public static final int PRIORITY = -250;

    @Override
    public PythonScript resolve(PythonScript pythonScript, Map<String, Object> arguments) {
        return pythonScript;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
