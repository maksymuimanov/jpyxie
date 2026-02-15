package io.jpyxie.python.executor;

import io.jpyxie.python.exception.PythonProcessReadingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class BasicPythonOutputProcessHandler implements ProcessOutputHandler {
    public static final String RESULT_PREFIX = "$";
    public static final boolean DEFAULT_LOGGABLE = true;
    private final boolean loggable;

    public BasicPythonOutputProcessHandler() {
        this(DEFAULT_LOGGABLE);
    }

    @Override
    public ProcessPythonResponse handle(Process process, PythonResultSpec resultSpec) {
        Map<String, String> resultJsons = new HashMap<>();
        resultSpec.forEach(requirement -> {
            try (BufferedReader bufferedReader = process.inputReader()) {
                bufferedReader.lines().forEach(line -> {
                    String fieldName = requirement.name();
                    String resultIdentifier = RESULT_PREFIX + fieldName;
                    if (line.startsWith(resultIdentifier)) {
                        String resultJson = line.replace(resultIdentifier, "");
                        resultJsons.put(fieldName, resultJson);
                    }
                    if (this.loggable) {
                        log.info(line);
                    }
                });
            } catch (IOException e) {
                throw new PythonProcessReadingException(e);
            }
        });

        return new ProcessPythonResponse(resultJsons);
    }
}