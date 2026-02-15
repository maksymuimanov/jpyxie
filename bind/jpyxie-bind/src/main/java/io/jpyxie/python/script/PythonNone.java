package io.jpyxie.python.script;

public class PythonNone implements PythonRepresentation {
    public static final String NONE = "None";

    @Override
    public String toPythonString() {
        return NONE;
    }
}
