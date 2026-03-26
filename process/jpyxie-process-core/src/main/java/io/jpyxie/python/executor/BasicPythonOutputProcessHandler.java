package io.jpyxie.python.executor;

import io.jpyxie.python.exception.PythonProcessReadingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class BasicPythonOutputProcessHandler implements ProcessOutputHandler {
    public static final boolean DEFAULT_LOGGABLE = true;
    private final boolean loggable;

    public BasicPythonOutputProcessHandler() {
        this(DEFAULT_LOGGABLE);
    }

    @Override
    public void handle(Process process) {
        this.logProcessOutput(process);
    }

    private void logProcessOutput(Process process) {
        if (this.loggable) {
            try (BufferedReader bufferedReader = process.inputReader()) {
                bufferedReader.lines()
                        .forEach(log::info);
            } catch (IOException e) {
                throw new PythonProcessReadingException(e);
            }
        }
    }
}