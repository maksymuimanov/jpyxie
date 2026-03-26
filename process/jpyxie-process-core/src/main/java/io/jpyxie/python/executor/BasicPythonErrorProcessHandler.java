package io.jpyxie.python.executor;

import io.jpyxie.python.exception.PythonProcessReadingException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class BasicPythonErrorProcessHandler implements ProcessErrorHandler {
    @Override
    public void handle(Process process) {
        try (BufferedReader bufferedReader = process.errorReader()) {
            String errorMessage = bufferedReader.lines().collect(Collectors.joining());
            if (!errorMessage.isBlank()) {
                log.error(errorMessage);
                throw new PythonProcessReadingException(errorMessage);
            }
        } catch (IOException e) {
            throw new PythonProcessReadingException(e);
        }
    }
}