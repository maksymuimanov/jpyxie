package io.maksymuimanov.python.executor;

public interface ProcessOutputHandler {
    ProcessPythonResponse handle(Process process, PythonResultSpec resultSpec);
}
