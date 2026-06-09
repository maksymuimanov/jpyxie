package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonList;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PythonListConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        List<?> list = (List<?>) object;
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
