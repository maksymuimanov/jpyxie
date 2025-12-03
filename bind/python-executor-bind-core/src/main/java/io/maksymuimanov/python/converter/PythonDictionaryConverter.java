package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonDictionary;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.util.HashMap;
import java.util.Map;

public class PythonDictionaryConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        Map<?, ?> map = ((Map<?, ?>) value);
        Map<PythonRepresentation, PythonRepresentation> representations = new HashMap<>(map.size());
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object entryKey = entry.getKey();
            Object entryValue = entry.getValue();
            PythonRepresentation dictionaryKey = pythonSerializer.serialize(entryKey);
            PythonRepresentation dictionaryValue = pythonSerializer.serialize(entryValue);
            representations.put(dictionaryKey, dictionaryValue);
        }

        return new PythonDictionary(representations);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isMap(type);
    }
}
