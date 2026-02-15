package io.jpyxie.python.script;

import java.math.BigDecimal;

public class PythonBigDecimal extends PythonValueContainer<BigDecimal> {
    public PythonBigDecimal(BigDecimal value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return "Decimal(\"" + this.getValue().toPlainString() + "\")";
    }
}
