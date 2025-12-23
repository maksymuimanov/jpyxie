package io.maksymuimanov.python.output;

import io.maksymuimanov.python.exception.PythonProcessReadingException;
import io.maksymuimanov.python.executor.ProcessPythonExecutor;
import io.maksymuimanov.python.executor.PythonResultDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Processes and handles the standard output stream of a given {@link Process}.
 *
 * <p>This {@link ProcessOutputHandler} implementation reads the standard output (stdout) of the process,
 * detects and extracts the body value marked by an appearance string, and returns it as a raw JSON string.
 *
 * <p>If {@link BasicPythonOutputProcessHandler#loggable} is enabled, all output lines
 * (including non-body lines) are logged at <code>INFO</code> level.
 *
 * <p>Example usage:
 * <pre>{@code
 * Process process = new ProcessBuilder("python", "script.py").start();
 * InputProcessHandler handler = new InputProcessHandler(executorProperties, resolverProperties);
 * String jsonResult = handler.handle(process);
 * if (jsonResult != null) {
 *     MyResult body = new ObjectMapper().readValue(jsonResult, MyResult.class);
 * }
 * }</pre>
 *
 * @see ProcessOutputHandler
 * @see ProcessPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class BasicPythonOutputProcessHandler implements ProcessOutputHandler {
    public static final String RESULT_PREFIX = "$";
    private final Map<String, String> resultMap;
    private final boolean loggable;

    public BasicPythonOutputProcessHandler(boolean loggable) {
        this(new LinkedHashMap<>(), loggable);
    }

    @Override
    public void handle(Process process, PythonResultDescription<?> resultDescription) {
        try (BufferedReader bufferedReader = process.inputReader()) {
            bufferedReader.lines().forEach(line -> {
                String fieldName = resultDescription.fieldName();
                String resultIdentifier = RESULT_PREFIX + fieldName;
                if (resultDescription.isVoid() && line.startsWith(resultIdentifier)) {
                    String resultJson = line.replace(resultIdentifier, "");
                    resultMap.put(fieldName, resultJson);
                }
                if (this.loggable) {
                    log.info(line);
                }
            });
        } catch (IOException e) {
            throw new PythonProcessReadingException(e);
        }
    }

    @Override
    public void handle(Process process, Iterable<PythonResultDescription<?>> resultDescriptions) {
        try (BufferedReader bufferedReader = process.inputReader()) {
            bufferedReader.lines().forEach(line -> {
                for (PythonResultDescription<?> resultDescription : resultDescriptions) {
                    String fieldName = resultDescription.fieldName();
                    String resultIdentifier = RESULT_PREFIX + fieldName;
                    if (resultDescription.isVoid() && line.startsWith(resultIdentifier)) {
                        String resultJson = line.replace(resultIdentifier, "");
                        resultMap.put(fieldName, resultJson);
                    }
                    if (this.loggable) {
                        log.info(line);
                    }   
                }
            });
        } catch (IOException e) {
            throw new PythonProcessReadingException(e);
        }
    }

    @Override
    public String getResult(String fieldName) {
        return this.getResultMap().get(fieldName);
    }

    @Override
    public Map<String, String> getResultMap() {
        return this.resultMap;
    }
}