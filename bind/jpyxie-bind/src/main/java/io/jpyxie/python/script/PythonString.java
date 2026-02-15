package io.jpyxie.python.script;

public class PythonString extends PythonValueContainer<String> {
    public PythonString(Object value) {
        this(String.valueOf(value));
    }

    public PythonString(String value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return "r\"\"\"" + this.getValue() + "\"\"\"";
    }
}