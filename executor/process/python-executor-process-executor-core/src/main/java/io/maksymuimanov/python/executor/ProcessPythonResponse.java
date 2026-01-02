package io.maksymuimanov.python.executor;

import java.util.Map;

public record ProcessPythonResponse(Map<String, String> results) {
}
