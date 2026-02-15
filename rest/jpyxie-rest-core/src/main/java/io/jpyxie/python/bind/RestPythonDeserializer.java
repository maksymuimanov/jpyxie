package io.jpyxie.python.bind;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jpyxie.python.executor.PythonResultRequirement;
import io.jpyxie.python.executor.RestPythonResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

@RequiredArgsConstructor
public class RestPythonDeserializer implements PythonDeserializer<RestPythonResponse> {
    private final ObjectMapper objectMapper;

    @Override
    @Nullable
    public <T> T deserialize(RestPythonResponse from, PythonResultRequirement<T> resultRequirement) {
        String name = resultRequirement.name();
        Class<T> type = resultRequirement.type();
        JsonNode tree = from.jsonTree();
        JsonNode resultNode = tree.get(name);
        return objectMapper.convertValue(resultNode, type);
    }
}
