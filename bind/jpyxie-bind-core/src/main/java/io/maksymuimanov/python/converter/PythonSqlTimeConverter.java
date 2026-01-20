package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonSerializer;
import io.maksymuimanov.python.bind.PythonTime;
import io.maksymuimanov.python.bind.PythonTypeConverter;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;

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
