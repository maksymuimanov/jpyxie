package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.annotation.PythonConvert;
import io.jpyxie.python.annotation.PythonDictionaryKey;
import io.jpyxie.python.annotation.PythonIgnore;
import io.jpyxie.python.annotation.PythonInclude;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonDictionary;
import io.jpyxie.python.bind.type.PythonString;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PythonObjectDictionaryConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        PythonDictionary pythonDictionary = new PythonDictionary();
        for (Field field : fields) {
            if (this.isSpecialField(field) || this.isIgnored(field) || this.isNotIncluded(clazz, field)) continue;
            field.setAccessible(true);
            PythonString pythonObjectKey = this.getDictionaryKey(field);
            PythonRepresentation pythonObjectValue = this.getDictionaryValue(pythonSerializer, field, object);
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
        return (visibleFields == PythonInclude.AccessModifier.PROTECTED
                    && !Modifier.isPublic(modifiers)
                    && !Modifier.isProtected(modifiers))
                || (visibleFields == PythonInclude.AccessModifier.PUBLIC
                    && !Modifier.isPublic(modifiers))
                || (!pythonInclude.staticFields()
                    && Modifier.isStatic(modifiers));
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
        } catch (Exception e) {
            throw PythonObjectDictionaryConverterException.failedToGetDictionaryValue(e);
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
