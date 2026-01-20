package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonDictionary;
import io.maksymuimanov.python.bind.PythonSerializer;
import io.maksymuimanov.python.bind.PythonString;
import io.maksymuimanov.python.bind.PythonTypeConverter;
import io.maksymuimanov.python.script.PythonRepresentation;

public class PythonEnumDictionaryConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        PythonDictionary dictionary = (PythonDictionary) pythonSerializer.serialize(value, PythonObjectDictionaryConverter.class);
        Enum<?> enumValue = (Enum<?>) value;
        dictionary.put(new PythonString("enum4java"), new PythonString(enumValue.name()));
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
