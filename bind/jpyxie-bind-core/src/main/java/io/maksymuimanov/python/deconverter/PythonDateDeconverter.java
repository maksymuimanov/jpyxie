package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonDate;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PythonDateDeconverter extends AbstractPythonTypeDeconverter<PythonDate, LocalDate> {
    protected static final Map<String, Integer> MONTHS = Arrays.stream(Month.values())
            .collect(Collectors.toUnmodifiableMap(month -> month.name().substring(0, 3), Month::getValue));
    protected static final Pattern YEAR_PATTERN = Pattern.compile("[0-9]{4}");
    protected static final Pattern MONTH_PATTERN = Pattern.compile("[A-Za-z]{3}");
    protected static final Pattern DAY_PATTERN = Pattern.compile("[0-9]{2}");
    protected static final int TOTAL_MONTHS = 12;

    @Override
    public @Nullable Object deconvert(PythonDate pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, LocalDate.class, pythonDeserializer);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonDate pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        LocalDate date = pythonRepresentation.getValue();
        if (LocalDate.class.equals(clazz)) {
            return (T) date;
        } else if (java.util.Date.class.equals(clazz)) {
            return (T) java.util.Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else if (java.sql.Date.class.equals(clazz)) {
            return (T) java.sql.Date.valueOf(date);
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonDate resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        LocalDate result = this.getValue(value, key -> {
            StringBuilder dateBuilder = new StringBuilder(key);
            int year = -1, month = -1, day = -1;

            Matcher yearMatcher = YEAR_PATTERN.matcher(dateBuilder);
            if (yearMatcher.find()) {
                String yearString = yearMatcher
                        .group();
                year = Integer.parseInt(yearString);
                dateBuilder.delete(yearMatcher.start(), yearMatcher.end());
            }

            Matcher monthMatcher = MONTH_PATTERN.matcher(dateBuilder);
            if (monthMatcher.find()) {
                String monthString = monthMatcher.group().toUpperCase();
                month = MONTHS.get(monthString);
                dateBuilder.delete(monthMatcher.start(), monthMatcher.end());
            }

            int first = -1, second = -1;
            Matcher dayMatcher = DAY_PATTERN.matcher(dateBuilder);
            while (dayMatcher.find()) {
                int datePart = Integer.parseInt(dayMatcher.group());
                if (month != -1) {
                    day = datePart;
                } else if (second == -1) {
                    if (first != -1) {
                        second = datePart;
                    } else {
                        first = datePart;
                    }
                } else {
                    break;
                }
            }

            if (month == -1) {
                if (first > TOTAL_MONTHS || second <= TOTAL_MONTHS) {
                    day = first;
                    month = second;
                } else {
                    month = first;
                    day = second;
                }
            }

            return LocalDate.of(year, month, day);
        });
        return new PythonDate(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return StringUtils.isDate(value);
    }
}
