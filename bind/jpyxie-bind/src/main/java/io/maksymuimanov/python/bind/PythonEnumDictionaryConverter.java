package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.script.PythonDictionary;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.script.PythonString;
import org.jspecify.annotations.Nullable;

public class PythonEnumDictionaryConverter implements PythonTypeConverter {
    public static final String ENUM_INSTANCE_NAME_KEY = "enum4java";

    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        PythonDictionary dictionary = (PythonDictionary) pythonSerializer.serialize(value, PythonObjectDictionaryConverter.class);
        Enum<?> enumValue = (Enum<?>) value;
        dictionary.put(new PythonString(ENUM_INSTANCE_NAME_KEY), new PythonString(enumValue.name()));
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
