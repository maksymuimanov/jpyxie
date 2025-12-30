package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.script.PythonScript;

public interface PythonExecutor {
    PythonResultContainer execute(PythonScript script, PythonResultSpec resultSpec);
}