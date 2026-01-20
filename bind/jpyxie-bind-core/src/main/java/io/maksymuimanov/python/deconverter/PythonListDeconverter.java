package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.bind.PythonList;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PythonListDeconverter extends AbstractPythonTypeDeconverter<PythonList, List<PythonRepresentation>> {
    @Override
    public @Nullable Object deconvert(PythonList pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, List.class, pythonDeserializer);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonList pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        Stream<?> stream = pythonRepresentation.getValue()
                .stream()
                .map(pr -> pythonDeserializer.deserialize(pythonRepresentation));
        if (List.class.equals(clazz)) {
            return (T) stream.toList();
        } else if (ArrayList.class.equals(clazz)) {
            return (T) stream.collect(Collectors.toCollection(ArrayList::new));
        } else if (LinkedList.class.equals(clazz)) {
            return (T) stream.collect(Collectors.toCollection(LinkedList::new));
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonList resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        List<PythonRepresentation> result = this.getValue(value, key -> {
            return List.of();
        });
        return new PythonList(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return StringUtils.isList(value);
    }
}
