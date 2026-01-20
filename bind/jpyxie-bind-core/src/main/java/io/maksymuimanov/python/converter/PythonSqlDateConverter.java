package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonDate;
import io.maksymuimanov.python.bind.PythonSerializer;
import io.maksymuimanov.python.bind.PythonTypeConverter;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.sql.Date;
import java.time.LocalDate;

public class PythonSqlDateConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        LocalDate date = ((Date) value).toLocalDate();
        return new PythonDate(date);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isSqlDate(type);
    }
}
