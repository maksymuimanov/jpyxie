package io.maksymuimanov.python.representation;

import java.math.BigInteger;

public class PythonBigInteger extends PythonValueContainer<BigInteger> {
    public PythonBigInteger(BigInteger value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return String.valueOf(this.getValue());
    }
}
