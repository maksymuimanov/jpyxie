package io.maksymuimanov.python.bind;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@UtilityClass
public class JavaTypeUtils {
    public boolean isInteger(Class<?> clazz) {
        return byte.class.isAssignableFrom(clazz)
                || Byte.class.isAssignableFrom(clazz)
                || short.class.isAssignableFrom(clazz)
                || Short.class.isAssignableFrom(clazz)
                || int.class.isAssignableFrom(clazz)
                || Integer.class.isAssignableFrom(clazz)
                || long.class.isAssignableFrom(clazz)
                || Long.class.isAssignableFrom(clazz);
    }

    public boolean isBigInteger(Class<?> clazz) {
        return BigInteger.class.isAssignableFrom(clazz);
    }

    public boolean isFloat(Class<?> clazz) {
        return float.class.isAssignableFrom(clazz)
                || Float.class.isAssignableFrom(clazz)
                || double.class.isAssignableFrom(clazz)
                || Double.class.isAssignableFrom(clazz);
    }

    public boolean isBigDecimal(Class<?> clazz) {
        return BigDecimal.class.isAssignableFrom(clazz);
    }

    public boolean isString(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz)
                || char.class.isAssignableFrom(clazz)
                || Character.class.isAssignableFrom(clazz);
    }

    public boolean isBoolean(Class<?> clazz) {
        return boolean.class.isAssignableFrom(clazz)
                || Boolean.class.isAssignableFrom(clazz);
    }

    public boolean isOptional(Class<?> clazz) {
        return Optional.class.isAssignableFrom(clazz);
    }

    public boolean isIterable(Class<?> clazz) {
        return Iterable.class.isAssignableFrom(clazz);
    }

    public boolean isList(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    public boolean isSet(Class<?> clazz) {
        return Set.class.isAssignableFrom(clazz);
    }

    public boolean isQueue(Class<?> clazz) {
        return Queue.class.isAssignableFrom(clazz);
    }

    public boolean isMap(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }
}
