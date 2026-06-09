package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonSet;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class PythonSetConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        Set<?> set = (Set<?>) object;
        Set<PythonRepresentation> representations = new HashSet<>(set.size());
        for (Object element : set) {
            representations.add(pythonSerializer.serialize(element));
        }
        return new PythonSet(representations);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isSet(type);
    }
}
