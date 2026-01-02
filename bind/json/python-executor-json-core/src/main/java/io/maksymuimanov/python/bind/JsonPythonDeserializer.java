package io.maksymuimanov.python.bind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonDeserializationException;
import io.maksymuimanov.python.executor.PythonResultRequirement;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class JsonPythonDeserializer implements PythonDeserializer<String> {
    private final Map<String, JsonNode> jsonTreeCache;
    private final ObjectMapper objectMapper;
    private final boolean cached;

    public JsonPythonDeserializer(ObjectMapper objectMapper, boolean cached) {
        this(new ConcurrentHashMap<>(), objectMapper, cached);
    }

    @Override
    @Nullable
    public <T> T deserialize(String from, PythonResultRequirement<T> resultRequirement) {
        JsonNode jsonTree = this.readCacheableJsonTree(from).get(resultRequirement.name());
        return this.convertJsonTree(jsonTree, resultRequirement);
    }

    private <T> T convertJsonTree(JsonNode node, PythonResultRequirement<T> resultRequirement) {
        try {
            return objectMapper.treeToValue(node, resultRequirement.type());
        } catch (JsonProcessingException e) {
            throw new PythonDeserializationException(e);
        }
    }

    private JsonNode readCacheableJsonTree(String from) {
        return this.cached ? this.jsonTreeCache.computeIfAbsent(from, this::readJsonTree) : this.readJsonTree(from);
    }

    private JsonNode readJsonTree(String from) {
        try {
            return objectMapper.readTree(from);
        } catch (JsonProcessingException e) {
            throw new PythonDeserializationException(e);
        }
    }
}
