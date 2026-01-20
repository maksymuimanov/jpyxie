package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.bind.PythonTime;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

public class PythonTimeDeconverter extends AbstractPythonTypeDeconverter<PythonTime, LocalTime> {
    public static final int TIME_PARTS_LENGTH = 4;

    @Override
    public @Nullable Object deconvert(PythonTime pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, LocalTime.class, pythonDeserializer);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonTime pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        LocalTime time = pythonRepresentation.getValue();
        if (LocalTime.class.equals(clazz)) {
            return (T) time;
        } else if (Time.class.equals(clazz)) {
            return (T) Time.valueOf(time);
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonTime resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        LocalTime result = this.getValue(value, key -> {
            int[] timeParts = new int[TIME_PARTS_LENGTH];
            Arrays.fill(timeParts, -1);
            int length = key.length();
            int notation = 1, partIndex = 0, currentPart = 0;
            for (int i = length - 1; i >= 0; i--) {
                char timeChar = value.charAt(i);
                boolean isDigit = Character.isDigit(timeChar);

                if (isDigit) {
                    int digit = Character.digit(timeChar, 10);
                    currentPart += notation * digit;
                    notation *= 10;
                }

                if ((!isDigit) || i == 0) {
                    timeParts[partIndex++] = currentPart;
                    currentPart = 0;
                    notation = 1;
                }
            }

            if (timeParts[3] != -1) {
                return LocalTime.of(timeParts[3], timeParts[2], timeParts[1], timeParts[0] * 1000);
            } else if (timeParts[2] != -1) {
                return LocalTime.of(timeParts[2], timeParts[1], timeParts[0]);
            } else if (timeParts[1] != -1) {
                return LocalTime.of(timeParts[1], timeParts[0]);
            } else {
                return LocalTime.of(timeParts[0], 0);
            }
        });

        return new PythonTime(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return StringUtils.isTime(value);
    }
}
