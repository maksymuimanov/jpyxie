package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.annotation.PythonConvert;
import io.maksymuimanov.python.annotation.PythonDictionaryKey;
import io.maksymuimanov.python.annotation.PythonIgnore;
import io.maksymuimanov.python.annotation.PythonInclude;
import io.maksymuimanov.python.bind.PythonDictionary;
import io.maksymuimanov.python.bind.PythonString;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PythonObjectDictionaryConverter implements PythonTypeConverter {
    @SneakyThrows
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        Class<?> clazz = value.getClass();
        PythonInclude pythonInclude = clazz.getDeclaredAnnotation(PythonInclude.class);
        Field[] fields = clazz.getDeclaredFields();
        PythonDictionary pythonDictionary = new PythonDictionary();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PythonIgnore.class)) continue;
            if (pythonInclude!= null) {
                PythonInclude.AccessModifier visibleFields = pythonInclude.visibleFields();
                int modifiers = field.getModifiers();
                if (visibleFields == PythonInclude.AccessModifier.PROTECTED
                        && !Modifier.isPublic(modifiers)
                        && !Modifier.isProtected(modifiers)) continue;
                else if (visibleFields == PythonInclude.AccessModifier.PUBLIC
                        && !Modifier.isPublic(modifiers)) continue;
                boolean staticFields = pythonInclude.staticFields();
                if (!staticFields && Modifier.isStatic(modifiers)) continue;
            }
            if (field.isEnumConstant() || field.isSynthetic()) continue;
            field.setAccessible(true);
            String dictionaryKey;
            if (field.isAnnotationPresent(PythonDictionaryKey.class)) {
                PythonDictionaryKey dictionaryKeyAnnotation = field.getDeclaredAnnotation(PythonDictionaryKey.class);
                dictionaryKey = dictionaryKeyAnnotation.value();
            } else {
                dictionaryKey = field.getName();
            }
            PythonString pythonObjectKey = new PythonString(dictionaryKey);
            Object fieldValue = field.get(value);
            PythonRepresentation pythonObjectValue;
            if (field.isAnnotationPresent(PythonConvert.class)) {
                PythonConvert pythonConvertAnnotation = field.getDeclaredAnnotation(PythonConvert.class);
                pythonObjectValue = pythonSerializer.serialize(fieldValue, pythonConvertAnnotation.value());
            } else {
                pythonObjectValue = pythonSerializer.serialize(fieldValue);
            }
            pythonDictionary.put(pythonObjectKey, pythonObjectValue);
        }

        return pythonDictionary;
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

    @Override
    public int getPriority() {
        return MAX_PRIORITY;
    }
}
