package io.maksymuimanov.python.output;

import io.maksymuimanov.python.executor.PythonResultDescription;

import java.util.Map;

public interface ProcessOutputHandler {
    void handle(Process process, PythonResultDescription<?> resultDescription);

    void handle(Process process, Iterable<PythonResultDescription<?>> resultDescriptions);

    String getResult(String fieldName);

    Map<String, String> getResultMap();
}
