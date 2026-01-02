package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonDateTime;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.sql.Timestamp;
import java.time.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PythonDateTimeDeconverter extends AbstractPythonTypeDeconverter<PythonDateTime, LocalDateTime> {
    public static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    @Override
    public @Nullable Object deconvert(PythonDateTime pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, LocalDateTime.class, pythonDeserializer);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonDateTime pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        LocalDateTime dateTime = pythonRepresentation.getValue();
        if (LocalDateTime.class.equals(clazz)) {
            return (T) dateTime;
        } else if (Timestamp.class.equals(clazz)) {
            return (T) Timestamp.valueOf(dateTime);
        } else if (ZonedDateTime.class.equals(clazz)) {
            return (T) ZonedDateTime.of(dateTime, ZoneId.systemDefault());
        } else if (OffsetDateTime.class.equals(clazz)) {
            return (T) OffsetDateTime.of(dateTime, (ZoneOffset) ZoneOffset.systemDefault());
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonDateTime resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        LocalDateTime result = this.getValue(value, key -> {
            int[] dateTimeParts = new int[7];
            Arrays.fill(dateTimeParts, 0);
            Matcher matcher = NUMBER_PATTERN.matcher(key);
            int partIndex = 0;
            while (matcher.find()) {
                String group = matcher.group();
                dateTimeParts[partIndex++] = Integer.parseInt(group);
            }

            return LocalDateTime.of(dateTimeParts[0], dateTimeParts[1], dateTimeParts[2], dateTimeParts[3], dateTimeParts[4], dateTimeParts[5], dateTimeParts[6] * 1000);
        });

        return new PythonDateTime(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return StringUtils.isDateTime(value);
    }
}
