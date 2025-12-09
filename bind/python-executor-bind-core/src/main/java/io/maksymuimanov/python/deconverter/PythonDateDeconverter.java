package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonDate;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PythonDateDeconverter extends AbstractPythonTypeDeconverter<PythonDate, LocalDate> {
    public static final Map<String, Integer> MONTHS = Arrays.stream(Month.values())
            .collect(Collectors.toUnmodifiableMap(month -> month.name().substring(0, 3), Month::getValue));
    public static final Pattern YEAR_PATTERN = Pattern.compile("[0-9]{4}");
    public static final Pattern MONTH_PATTERN = Pattern.compile("[A-Za-z]{3}");
    public static final Pattern DAY_PATTERN = Pattern.compile("[0-9]{2}");
    public static final int TOTAL_MONTHS = 12;

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

            Matcher dayMatcher = DAY_PATTERN.matcher(dateBuilder);
            while (dayMatcher.find()) {
                int datePart = Integer.parseInt(dayMatcher.group());
                if (month != -1) {
                    day = datePart;
                } else if (day == -1) {
                    if (datePart > TOTAL_MONTHS) {
                        day = datePart;
                    } else {
                        month = datePart;
                    }
                } else {
                    break;
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
