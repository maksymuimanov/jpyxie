package io.maksymuimanov.python.bind;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.*;

@UtilityClass
public class JavaTypeUtils {
    public boolean isInteger(Class<?> clazz) {
        return is(byte.class, clazz) || is(Byte.class, clazz)
                || is(short.class, clazz) || is(Short.class, clazz)
                || is(int.class, clazz) || is(Integer.class, clazz)
                || is(long.class, clazz) || is(Long.class, clazz);
    }

    public boolean isBigInteger(Class<?> clazz) {
        return is(BigInteger.class, clazz);
    }

    public boolean isFloat(Class<?> clazz) {
        return is(float.class, clazz) || is(Float.class, clazz)
                || is(double.class, clazz) || is(Double.class, clazz);
    }

    public boolean isBigDecimal(Class<?> clazz) {
        return is(BigDecimal.class, clazz);
    }

    public boolean isString(Class<?> clazz) {
        return is(String.class, clazz) || is(char.class, clazz) || is(Character.class, clazz);
    }

    public boolean isBoolean(Class<?> clazz) {
        return is(boolean.class, clazz) || is(Boolean.class, clazz);
    }

    public boolean isOptional(Class<?> clazz) {
        return is(Optional.class, clazz);
    }

    public boolean isIterable(Class<?> clazz) {
        return is(Iterable.class, clazz);
    }

    public boolean isList(Class<?> clazz) {
        return is(List.class, clazz);
    }

    public boolean isSet(Class<?> clazz) {
        return is(Set.class, clazz);
    }

    public boolean isQueue(Class<?> clazz) {
        return is(Queue.class, clazz);
    }

    public boolean isMap(Class<?> clazz) {
        return is(Map.class, clazz);
    }

    public boolean isCalendar(Class<?> clazz) {
        return is(Calendar.class, clazz);
    }

    public boolean isDate(Class<?> clazz) {
        return is(Date.class, clazz);
    }

    public boolean isDuration(Class<?> clazz) {
        return is(Duration.class, clazz);
    }

    public boolean isInstant(Class<?> clazz) {
        return is(Instant.class, clazz);
    }

    public boolean isLocalDate(Class<?> clazz) {
        return is(LocalDate.class, clazz);
    }

    public boolean isLocalDateTime(Class<?> clazz) {
        return is(LocalDateTime.class, clazz);
    }

    public boolean isLocalTime(Class<?> clazz) {
        return is(LocalTime.class, clazz);
    }

    public boolean isOffsetDateTime(Class<?> clazz) {
        return is(OffsetDateTime.class, clazz);
    }

    public boolean isPeriod(Class<?> clazz) {
        return is(Period.class, clazz);
    }

    public boolean isSqlDate(Class<?> clazz) {
        return is(java.sql.Date.class, clazz);
    }

    public boolean isSqlTime(Class<?> clazz) {
        return is(java.sql.Time.class, clazz);
    }

    public boolean isSqlTimestamp(Class<?> clazz) {
        return is(java.sql.Timestamp.class, clazz);
    }

    public boolean isZonedDateTime(Class<?> clazz) {
        return is(ZonedDateTime.class, clazz);
    }

    public boolean isZoneId(Class<?> clazz) {
        return is(ZoneId.class, clazz);
    }

    public boolean is(Class<?> parent, Class<?> clazz) {
        return parent.isAssignableFrom(clazz);
    }
}
