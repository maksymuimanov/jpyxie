package io.maksymuimanov.python.executor;

import com.fasterxml.jackson.databind.JsonNode;

public record RestPythonResponse(JsonNode jsonTree) {
}
