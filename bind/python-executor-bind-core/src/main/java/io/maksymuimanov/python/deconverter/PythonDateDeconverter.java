package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonDate;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class PythonDateDeconverter extends AbstractPythonTypeDeconverter<PythonDate, LocalDate> {
    public static final int DEFAULT_DATE_PARTS = 3;

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonDate pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        LocalDate date = pythonRepresentation.getValue();
        if (LocalDate.class.equals(clazz)) {
            return (T) date;
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonDate resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        LocalDate result = this.getValue(value, (key) -> {
            List<Integer> dateParts = new ArrayList<>(DEFAULT_DATE_PARTS);
            int current = 0;
            int base = 1;
            boolean isReading = true;
            for (int i = key.length() - 1; i >= 0; i--) {
                char aChar = key.charAt(i);
                boolean isDigit = Character.isDigit(aChar);

                if (isDigit) {
                    int digit = Character.digit(aChar, 10) * base;
                    current += digit;
                    base *= 10;
                    isReading = true;
                }

                if ((!isDigit && isReading)) {
                    dateParts.add(0, current);
                    current = 0;
                    base = 1;
                    isReading = false;
                }
            }

            if (dateParts.size() != DEFAULT_DATE_PARTS) {
                dateParts.add(0, current);
            }

            int first, second, third;
            if (dateParts.size() == 1) {
                int fullDate = dateParts.get(0);
                first = fullDate % 100;
                second = (fullDate / 100) % 100;
                third = fullDate / 10000;
            } else {
                first = dateParts.get(0);
                second = dateParts.get(1);
                third = dateParts.get(2);
            }

            int year, month, day;
            if (this.isDayOfMonth(first) && this.isMonth(second)) {
                year = third;
                month = second;
                day = first;
            } else if (this.isDayOfMonth(second) && this.isMonth(first)) {
                year = third;
                month = first;
                day = second;
            } else if (this.isDayOfMonth(second) && this.isMonth(third)) {
                year = first;
                month = third;
                day = second;
            } else if (this.isDayOfMonth(third) && this.isMonth(second)) {
                year = first;
                month = second;
                day = third;
            } else {
                throw new DateTimeException("Invalid date: " + first + "-" + second + "-" + third);
            }
            return LocalDate.of(year, month, day);
        });
        return new PythonDate(result);
    }

    protected boolean isDayOfMonth(int second) {
        return ChronoField.DAY_OF_MONTH.range().isValidValue(second);
    }

    protected boolean isMonth(int third) {
        return ChronoField.MONTH_OF_YEAR.range().isValidValue(third);
    }

    @Override
    public boolean matches(CharSequence value) {
        return StringUtils.isDate(value);
    }
}
