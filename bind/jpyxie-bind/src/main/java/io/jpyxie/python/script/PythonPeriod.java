package io.jpyxie.python.script;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class PythonPeriod extends PythonValueContainer<Period> {
    public PythonPeriod(Period value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        LocalDate now = LocalDate.now();
        float days = ChronoUnit.DAYS.between(now, now.plus(this.getValue()));
        return "timedelta(days=" + days + ")";
    }
}
