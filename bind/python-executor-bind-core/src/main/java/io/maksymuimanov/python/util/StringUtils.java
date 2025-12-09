package io.maksymuimanov.python.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {
    public final Pattern INT_PATTERN = Pattern.compile("^[+-]?[0-9]+$");
    public final Pattern FLOAT_PATTERN = Pattern.compile("^[+-]?[0-9]+[.]?[0-9]+$");
    public final Pattern BOOLEAN_PATTERN = Pattern.compile("^(True|False)$");
    public final Pattern DATE_PATTERN = Pattern.compile("^([0-9]{4}\\D*[0-9]{2}\\D*[0-9]{2}|[0-9]{2}\\D*[0-9]{2}\\D*[0-9]{4}|[0-9]{4}\\D*[A-Za-z]{3}\\D*[0-9]{2}|[0-9]{4}\\D*[0-9]{2}\\D*[A-Za-z]{3}|[0-9]{2}\\D*[A-Za-z]{3}\\D*[0-9]{4}|[A-Za-z]{3}\\D*[0-9]{2}\\D*[0-9]{4})$");

    public boolean isInteger(CharSequence value) {
        return isPattern(value, INT_PATTERN);
    }

    public boolean isFloat(CharSequence value) {
        return isPattern(value, FLOAT_PATTERN);
    }

    public boolean isBoolean(CharSequence value) {
        return isPattern(value, BOOLEAN_PATTERN);
    }

    public boolean isDate(CharSequence value) {
        return isPattern(value, DATE_PATTERN);
    }

    public boolean isPattern(CharSequence value, Pattern pattern) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
