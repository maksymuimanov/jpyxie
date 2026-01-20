package io.maksymuimanov.python.bind;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

public class PythonZonedDateTime extends PythonValueContainer<ZonedDateTime> {
    public PythonZonedDateTime(ZonedDateTime value) {
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
                String.valueOf(this.getValue().get(ChronoField.MICRO_OF_SECOND)),
                "tzinfo=ZoneInfo(\"" + this.getValue().getZone().getId() + "\")"
        ) + ")";
    }
}
