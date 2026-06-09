package io.jpyxie.python.bind.type;

public class PythonFloat extends PythonValueContainer<Number> {
    public PythonFloat(Number value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return String.valueOf(this.getValue().doubleValue());
    }
}
