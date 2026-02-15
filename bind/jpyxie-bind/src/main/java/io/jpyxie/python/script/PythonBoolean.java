package io.jpyxie.python.script;

public class PythonBoolean extends PythonValueContainer<Boolean> {
    public static final String TRUE = "True";
    public static final String FALSE = "False";

    public PythonBoolean(Boolean value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return this.getValue() ? TRUE : FALSE;
    }
}
