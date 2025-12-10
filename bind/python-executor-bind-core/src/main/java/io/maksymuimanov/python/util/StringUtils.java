package io.maksymuimanov.python.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
    public static final Pattern INT_PATTERN = Pattern.compile("^[+-]?\\d+$");
    public static final Pattern FLOAT_PATTERN = Pattern.compile("^[+-]?\\d+[.]?\\d+$");
    public static final Pattern BOOLEAN_PATTERN = Pattern.compile("^(True|False)$");
    public static final Pattern DATE_PATTERN = Pattern.compile("^((\\d{4}\\D*\\d{2}\\D*\\d{2})|(\\d{2}\\D*\\d{2}\\D*\\d{4})|(\\d{4}\\D*[A-Za-z]{3}\\D*\\d{2})|(\\d{4}\\D*\\d{2}\\D*[A-Za-z]{3})|(\\d{2}\\D*[A-Za-z]{3}\\D*\\d{4})|([A-Za-z]{3}\\D*\\d{2}\\D*\\d{4}))$");
    public static final Pattern TIME_PATTERN = Pattern.compile("^(\\d{1,2}\\D*\\d{1,2}\\D*\\d{0,2}\\D*\\d*)$");
    public static final Pattern DATE_TIME_PATTERN = Pattern.compile("^((\\d{4}\\D*\\d{2}\\D*\\d{2})|(\\d{2}\\D*\\d{2}\\D*\\d{4})|(\\d{4}\\D*[A-Za-z]{3}\\D*\\d{2})|(\\d{4}\\D*\\d{2}\\D*[A-Za-z]{3})|(\\d{2}\\D*[A-Za-z]{3}\\D*\\d{4})|([A-Za-z]{3}\\D*\\d{2}\\D*\\d{4}))\\D*(\\d{1,2}\\D*\\d{1,2}\\D*\\d{0,2}\\D*\\d+)$");
    public static final Pattern LIST_PATTERN = Pattern.compile("^\\[\\s*(?:[^\\[\\]]+|\\[[^\\[\\]]*])*(?:\\s*,\\s*(?:[^\\[\\]]+|\\[[^\\[\\]]*]))*\\s*]$");

    private StringUtils() {
    }

    public static boolean isInteger(CharSequence value) {
        return isPattern(value, INT_PATTERN);
    }

    public static boolean isFloat(CharSequence value) {
        return isPattern(value, FLOAT_PATTERN);
    }

    public static boolean isBoolean(CharSequence value) {
        return isPattern(value, BOOLEAN_PATTERN);
    }

    public static boolean isDate(CharSequence value) {
        return isPattern(value, DATE_PATTERN);
    }

    public static boolean isTime(CharSequence value) {
        return isPattern(value, TIME_PATTERN);
    }

    public static boolean isDateTime(CharSequence value) {
        return isPattern(value, DATE_TIME_PATTERN);
    }

    public static boolean isList(CharSequence value) {
        return isPattern(value, LIST_PATTERN);
    }

    public static boolean isPattern(CharSequence value, Pattern pattern) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
