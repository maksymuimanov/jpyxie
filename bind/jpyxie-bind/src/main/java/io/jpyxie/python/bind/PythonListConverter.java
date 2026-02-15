package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonList;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
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
