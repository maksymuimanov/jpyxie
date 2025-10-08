package io.w4t3rcs.python.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class TestUtils {
    @SuppressWarnings("unchecked")
    public <T> void setField(T t, String fieldName, Object value) {
        try {
            Class<? extends T> clazz = (Class<? extends T>) t.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(t, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
