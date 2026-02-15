package io.jpyxie.python.executor;

public interface ProcessOutputHandler {
    ProcessPythonResponse handle(Process process, PythonResultSpec resultSpec);
}
