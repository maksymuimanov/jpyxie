package io.maksymuimanov.python.executor;

import java.util.List;

public record PythonRestRequest(String script, List<String> fieldNames) {
}
