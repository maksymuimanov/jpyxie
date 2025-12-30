package io.maksymuimanov.python.output;

import io.maksymuimanov.python.executor.PythonResultSpec;

import java.util.Map;

public interface ProcessOutputHandler {
    void handle(Process process, PythonResultSpec<?> resultDescription);

    void handle(Process process, Iterable<PythonResultSpec<?>> resultDescriptions);

    String getResult(String fieldName);

    Map<String, String> getResultMap();
}
