package io.maksymuimanov.python.representation;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;

public class PythonOffsetDateTime extends PythonValueContainer<OffsetDateTime> {
    public PythonOffsetDateTime(OffsetDateTime value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        Duration offset = Duration.ofSeconds(this.getValue().getOffset().getTotalSeconds());
        return "datetime(" + String.join(", ",
                String.valueOf(this.getValue().getYear()),
                String.valueOf(this.getValue().getMonthValue()),
                String.valueOf(this.getValue().getDayOfMonth()),
                String.valueOf(this.getValue().getHour()),
                String.valueOf(this.getValue().getMinute()),
                String.valueOf(this.getValue().getSecond()),
                String.valueOf(this.getValue().get(ChronoField.MICRO_OF_SECOND)),
                "timezone(timedelta(hours=" + offset.toHoursPart() + ", minutes=" + offset.toMinutesPart() + ")"
        ) + ")";
    }
}
