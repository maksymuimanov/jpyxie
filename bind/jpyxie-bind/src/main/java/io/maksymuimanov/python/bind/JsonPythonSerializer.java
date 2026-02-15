package io.maksymuimanov.python.bind;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonSerializationException;
import io.maksymuimanov.python.script.PythonJson;
import io.maksymuimanov.python.script.PythonNone;
import io.maksymuimanov.python.script.PythonRepresentation;
import org.jspecify.annotations.Nullable;

public class JsonPythonSerializer implements PythonSerializer {
    private final ObjectMapper objectMapper;

    public JsonPythonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public PythonRepresentation serialize(@Nullable Object o) {
        try {
            if (o == null) return new PythonNone();
            String jsonString = this.objectMapper.writeValueAsString(o);
            return new PythonJson(jsonString);
        } catch (Exception e) {
            throw new PythonSerializationException(e);
        }
    }

    @Override
    public PythonRepresentation serialize(@Nullable Object o, Class<? extends PythonTypeConverter> typeConverterClass) {
        throw new UnsupportedOperationException();
    }
}
