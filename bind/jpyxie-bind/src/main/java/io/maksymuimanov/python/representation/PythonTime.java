package io.maksymuimanov.python.representation;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class PythonTime extends PythonValueContainer<LocalTime> {
    public PythonTime(LocalTime value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return "time(" + String.join(", ",
                String.valueOf(this.getValue().getHour()),
                String.valueOf(this.getValue().getMinute()),
                String.valueOf(this.getValue().getSecond()),
                String.valueOf(this.getValue().get(ChronoField.MICRO_OF_SECOND))
        ) + ")";
    }
}
