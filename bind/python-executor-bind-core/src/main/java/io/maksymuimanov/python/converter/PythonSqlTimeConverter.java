package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonTime;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

import java.sql.Time;
import java.time.LocalTime;

public class PythonSqlTimeConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        LocalTime time = ((Time) value).toLocalTime();
        return new PythonTime(time);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isSqlTime(type);
    }
}
