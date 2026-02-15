package io.maksymuimanov.python.representation;

import io.maksymuimanov.python.script.PythonRepresentation;

public class PythonNone implements PythonRepresentation {
    public static final String NONE = "None";

    @Override
    public String toPythonString() {
        return NONE;
    }
}
