package io.maksymuimanov.python.output;

import io.maksymuimanov.python.executor.PythonResultSpec;

import java.util.Map;

public interface ProcessOutputHandler {
    Map<String, String> handle(Process process, PythonResultSpec resultSpec);
}
