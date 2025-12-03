package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonDateTime;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PythonSqlTimestampConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        LocalDateTime dateTime = ((Timestamp) value).toLocalDateTime();
        return new PythonDateTime(dateTime);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isSqlTimestamp(type);
    }
}
