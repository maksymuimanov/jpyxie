package io.maksymuimanov.python.script;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class PythonDateTime extends PythonValueContainer<LocalDateTime> {
    public PythonDateTime(LocalDateTime value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return "datetime(" + String.join(", ",
                String.valueOf(this.getValue().getYear()),
                String.valueOf(this.getValue().getMonthValue()),
                String.valueOf(this.getValue().getDayOfMonth()),
                String.valueOf(this.getValue().getHour()),
                String.valueOf(this.getValue().getMinute()),
                String.valueOf(this.getValue().getSecond()),
                String.valueOf(this.getValue().get(ChronoField.MICRO_OF_SECOND))
        ) + ")";
    }
}
