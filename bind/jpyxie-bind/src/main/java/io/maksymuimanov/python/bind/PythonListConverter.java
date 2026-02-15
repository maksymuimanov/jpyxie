package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.script.PythonList;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PythonListConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        List<?> list = (List<?>) value;
        List<PythonRepresentation> representations = new ArrayList<>(list.size());
        for (Object element : list) {
            representations.add(pythonSerializer.serialize(element));
        }
        return new PythonList(representations);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isList(type);
    }
}
