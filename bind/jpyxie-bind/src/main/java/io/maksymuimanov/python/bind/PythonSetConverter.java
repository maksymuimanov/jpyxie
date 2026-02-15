package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.representation.PythonSet;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class PythonSetConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        Set<?> set = (Set<?>) value;
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
