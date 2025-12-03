package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonInstant;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.time.Instant;
import java.util.Calendar;

public class PythonCalendarConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        Instant instant = ((Calendar) value).toInstant();
        return new PythonInstant(instant);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isCalendar(type);
    }
}
