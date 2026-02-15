package io.maksymuimanov.python.script;

import java.time.LocalDate;

public class PythonDate extends PythonValueContainer<LocalDate> {
    public PythonDate(LocalDate value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return "date(" + String.join(", ",
                String.valueOf(this.getValue().getYear()),
                String.valueOf(this.getValue().getMonthValue()),
                String.valueOf(this.getValue().getDayOfMonth())
        ) + ")";
    }
}
