package io.maksymuimanov.python.executor;

import java.util.List;

public record RestPythonRequest(String script, List<String> fieldNames) {
}
