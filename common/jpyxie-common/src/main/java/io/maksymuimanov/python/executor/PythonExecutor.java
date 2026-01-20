package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;

public interface PythonExecutor {
    PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec);
}