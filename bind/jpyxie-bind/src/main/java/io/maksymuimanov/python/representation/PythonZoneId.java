package io.maksymuimanov.python.representation;

import java.time.ZoneId;

public class PythonZoneId extends PythonValueContainer<ZoneId> {
    public PythonZoneId(ZoneId value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return "ZoneInfo(" + this.getValue().getId() + ")";
    }
}
