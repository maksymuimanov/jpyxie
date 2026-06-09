package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonDictionary;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PythonDictionaryConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        Map<?, ?> map = ((Map<?, ?>) object);
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
