package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.annotation.PythonConvert;
import io.maksymuimanov.python.annotation.PythonDictionaryKey;
import io.maksymuimanov.python.annotation.PythonIgnore;
import io.maksymuimanov.python.annotation.PythonInclude;
import io.maksymuimanov.python.exception.PythonTypeConversionException;
import io.maksymuimanov.python.script.PythonDictionary;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.script.PythonString;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PythonObjectDictionaryConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        Class<?> clazz = value.getClass();
        Field[] fields = clazz.getDeclaredFields();
        PythonDictionary pythonDictionary = new PythonDictionary();
        for (Field field : fields) {
            if (this.isSpecialField(field) || this.isIgnored(field) || this.isNotIncluded(clazz, field)) continue;
            field.setAccessible(true);
            PythonString pythonObjectKey = this.getDictionaryKey(field);
            PythonRepresentation pythonObjectValue = this.getDictionaryValue(pythonSerializer, field, value);
            pythonDictionary.put(pythonObjectKey, pythonObjectValue);
        }

        return pythonDictionary;
    }

    protected boolean isSpecialField(Field field) {
        return field.isEnumConstant() || field.isSynthetic();
    }

    protected boolean isIgnored(Field field) {
        return field.isAnnotationPresent(PythonIgnore.class);
    }

    protected boolean isNotIncluded(Class<?> clazz, Field field) {
        if (!clazz.isAnnotationPresent(PythonInclude.class)) return false;
        PythonInclude pythonInclude = clazz.getDeclaredAnnotation(PythonInclude.class);
        PythonInclude.AccessModifier visibleFields = pythonInclude.visibleFields();
        int modifiers = field.getModifiers();
        if (visibleFields == PythonInclude.AccessModifier.PROTECTED
                && !Modifier.isPublic(modifiers)
                && !Modifier.isProtected(modifiers)) return true;
        else if (visibleFields == PythonInclude.AccessModifier.PUBLIC
                && !Modifier.isPublic(modifiers)) return true;
        return !pythonInclude.staticFields() && Modifier.isStatic(modifiers);
    }

    protected PythonString getDictionaryKey(Field field) {
        String dictionaryKey;
        if (field.isAnnotationPresent(PythonDictionaryKey.class)) {
            PythonDictionaryKey dictionaryKeyAnnotation = field.getDeclaredAnnotation(PythonDictionaryKey.class);
            dictionaryKey = dictionaryKeyAnnotation.value();
        } else {
            dictionaryKey = field.getName();
        }
        return new PythonString(dictionaryKey);
    }

    protected PythonRepresentation getDictionaryValue(PythonSerializer pythonSerializer, Field field, Object value) {
        try {
            Object fieldValue = field.get(value);
            PythonRepresentation pythonObjectValue;
            if (field.isAnnotationPresent(PythonConvert.class)) {
                PythonConvert pythonConvertAnnotation = field.getDeclaredAnnotation(PythonConvert.class);
                pythonObjectValue = pythonSerializer.serialize(fieldValue, pythonConvertAnnotation.value());
            } else {
                pythonObjectValue = pythonSerializer.serialize(fieldValue);
            }
            return pythonObjectValue;
        } catch (IllegalAccessException e) {
            throw new PythonTypeConversionException(e);
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

    @Override
    public int getPriority() {
        return MIN_PRIORITY;
    }
}
