package io.maksymuimanov.python.representation;

import java.time.Duration;

public class PythonDuration extends PythonValueContainer<Duration> {
    public PythonDuration(Duration value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return "timedelta(microseconds=" + this.getValue().toNanos() / 1000 + ")";
    }
}
