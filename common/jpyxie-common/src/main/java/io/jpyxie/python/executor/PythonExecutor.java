package io.jpyxie.python.executor;

import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;

public interface PythonExecutor {
    PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec);
}