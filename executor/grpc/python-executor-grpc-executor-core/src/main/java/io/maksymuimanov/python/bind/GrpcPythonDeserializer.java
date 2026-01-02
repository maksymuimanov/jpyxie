package io.maksymuimanov.python.bind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonDeserializationException;
import io.maksymuimanov.python.executor.PythonResultRequirement;
import io.maksymuimanov.python.proto.GrpcPythonResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

@RequiredArgsConstructor
public class GrpcPythonDeserializer implements PythonDeserializer<GrpcPythonResponse> {
    private final ObjectMapper objectMapper;

    @Override
    @Nullable
    public <T> T deserialize(GrpcPythonResponse from, PythonResultRequirement<T> resultRequirement) {
        try {
            String name = resultRequirement.name();
            Class<T> type = resultRequirement.type();
            String resultJson = from.getFieldsOrThrow(name);
            return objectMapper.readValue(resultJson, type);
        } catch (JsonProcessingException e) {
            throw new PythonDeserializationException(e);
        }
    }
}
