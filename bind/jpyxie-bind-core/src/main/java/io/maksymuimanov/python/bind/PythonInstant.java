package io.maksymuimanov.python.bind;

import java.time.Instant;

public class PythonInstant extends PythonValueContainer<Instant> {
    public PythonInstant(Instant value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return "datetime.fromtimestamp(" + this.getValue().getEpochSecond() + ", tz=timezone.utc)";
    }
}
