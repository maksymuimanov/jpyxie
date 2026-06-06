package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonDictionary;
import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.script.PythonString;
import org.jspecify.annotations.Nullable;

public class PythonEnumDictionaryConverter implements PythonTypeConverter {
    public static final String ENUM_INSTANCE_NAME_KEY = "enum4java";

    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        PythonDictionary dictionary = (PythonDictionary) pythonSerializer.serialize(object, PythonObjectDictionaryConverter.class);
        Enum<?> enumValue = (Enum<?>) object;
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
