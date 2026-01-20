package io.maksymuimanov.python.bind;

public class PythonInt extends PythonValueContainer<Number> {
    public PythonInt(Number value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return String.valueOf(this.getValue().longValue());
    }
}
