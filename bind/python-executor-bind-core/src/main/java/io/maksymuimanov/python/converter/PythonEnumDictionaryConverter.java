package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonDictionary;
import io.maksymuimanov.python.bind.PythonString;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import lombok.SneakyThrows;

public class PythonEnumDictionaryConverter implements PythonTypeConverter {
    @SneakyThrows
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        PythonDictionary dictionary = (PythonDictionary) pythonSerializer.serialize(value, PythonObjectDictionaryConverter.class);
        Enum<?> enumValue = (Enum<?>) value;
        dictionary.put(new PythonString("$enumName"), new PythonString(enumValue.name()));
        return dictionary;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public int getPriority() {
        return LOW_PRIORITY;
    }
}
