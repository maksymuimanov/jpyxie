package io.maksymuimanov.python.bind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonDeserializationException;
import io.maksymuimanov.python.executor.ProcessPythonResponse;
import io.maksymuimanov.python.executor.PythonResultRequirement;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.util.Map;

@RequiredArgsConstructor
public class ProcessPythonDeserializer implements PythonDeserializer<ProcessPythonResponse> {
    private final ObjectMapper objectMapper;

    @Override
    @Nullable
    public <T> T deserialize(ProcessPythonResponse from, PythonResultRequirement<T> resultRequirement) {
        try {
            String name = resultRequirement.name();
            Class<T> type = resultRequirement.type();
            Map<String, String> resultJsons = from.results();
            String resultJson = resultJsons.get(name);
            return objectMapper.readValue(resultJson, type);
        } catch (JsonProcessingException e) {
            throw new PythonDeserializationException(e);
        }
    }
}
