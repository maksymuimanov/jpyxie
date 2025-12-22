package io.maksymuimanov.python.input;

import io.maksymuimanov.python.executor.PythonResultDescription;

public interface ProcessInputHandler {
    void handle(Process process, PythonResultDescription<?> resultDescription);

    void handle(Process process, Iterable<PythonResultDescription<?>> resultDescriptions);
}
