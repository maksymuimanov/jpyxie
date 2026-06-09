package io.jpyxie.python.bind.type;

import io.jpyxie.python.PythonRepresentation;

public class PythonNone implements PythonRepresentation {
    public static final String NONE = "None";

    @Override
    public String toPythonString() {
        return NONE;
    }
}
