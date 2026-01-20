package io.maksymuimanov.python.bind;

public class PythonString extends PythonValueContainer<String> {
    public PythonString(Object value) {
        this(String.valueOf(value));
    }

    public PythonString(String value) {
        super(value.replace("\"", "\\\""));
    }

    @Override
    public String toPythonString() {
        return "\"" + this.getValue() + "\"";
    }
}