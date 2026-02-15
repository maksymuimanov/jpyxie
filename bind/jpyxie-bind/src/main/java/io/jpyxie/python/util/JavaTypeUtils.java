package io.jpyxie.python.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.*;

public final class JavaTypeUtils {
    private JavaTypeUtils() {
    }

    public static boolean isInteger(Class<?> clazz) {
        return is(byte.class, clazz) || is(Byte.class, clazz)
                || is(short.class, clazz) || is(Short.class, clazz)
                || is(int.class, clazz) || is(Integer.class, clazz)
                || is(long.class, clazz) || is(Long.class, clazz);
    }

    public static boolean isBigInteger(Class<?> clazz) {
        return is(BigInteger.class, clazz);
    }

    public static boolean isFloat(Class<?> clazz) {
        return is(float.class, clazz) || is(Float.class, clazz)
                || is(double.class, clazz) || is(Double.class, clazz);
    }

    public static boolean isBigDecimal(Class<?> clazz) {
        return is(BigDecimal.class, clazz);
    }

    public static boolean isString(Class<?> clazz) {
        return is(String.class, clazz) || is(char.class, clazz) || is(Character.class, clazz);
    }

    public static boolean isBoolean(Class<?> clazz) {
        return is(boolean.class, clazz) || is(Boolean.class, clazz);
    }

    public static boolean isOptional(Class<?> clazz) {
        return is(Optional.class, clazz);
    }

    public static boolean isIterable(Class<?> clazz) {
        return is(Iterable.class, clazz);
    }

    public static boolean isList(Class<?> clazz) {
        return is(List.class, clazz);
    }

    public static boolean isSet(Class<?> clazz) {
        return is(Set.class, clazz);
    }

    public static boolean isQueue(Class<?> clazz) {
        return is(Queue.class, clazz);
    }

    public static boolean isMap(Class<?> clazz) {
        return is(Map.class, clazz);
    }

    public static boolean isCalendar(Class<?> clazz) {
        return is(Calendar.class, clazz);
    }

    public static boolean isDate(Class<?> clazz) {
        return is(Date.class, clazz);
    }

    public static boolean isDuration(Class<?> clazz) {
        return is(Duration.class, clazz);
    }

    public static boolean isInstant(Class<?> clazz) {
        return is(Instant.class, clazz);
    }

    public static boolean isLocalDate(Class<?> clazz) {
        return is(LocalDate.class, clazz);
    }

    public static boolean isLocalDateTime(Class<?> clazz) {
        return is(LocalDateTime.class, clazz);
    }

    public static boolean isLocalTime(Class<?> clazz) {
        return is(LocalTime.class, clazz);
    }

    public static boolean isOffsetDateTime(Class<?> clazz) {
        return is(OffsetDateTime.class, clazz);
    }

    public static boolean isPeriod(Class<?> clazz) {
        return is(Period.class, clazz);
    }

    public static boolean isZonedDateTime(Class<?> clazz) {
        return is(ZonedDateTime.class, clazz);
    }

    public static boolean isZoneId(Class<?> clazz) {
        return is(ZoneId.class, clazz);
    }

    public static boolean is(Class<?> parent, Class<?> clazz) {
        return parent.isAssignableFrom(clazz);
    }
}
