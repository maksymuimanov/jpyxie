package io.w4t3rcs.python.input.impl;

import io.w4t3rcs.python.exception.ProcessReadingException;
import io.w4t3rcs.python.executor.ProcessPythonExecutor;
import io.w4t3rcs.python.input.ProcessHandler;
import io.w4t3rcs.python.properties.ProcessPythonExecutorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Processes and handles the standard output stream of a given {@link Process}.
 *
 * <p>This {@link ProcessHandler} implementation reads the standard output (stdout) of the process,
 * detects and extracts the body value marked by a configured appearance string from
 * {@link ProcessPythonExecutorProperties#resultAppearance()}, and returns it as a raw JSON string.
 *
 * <p>If {@link ProcessPythonExecutorProperties#loggable()} is enabled, all output lines
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
 * @see ProcessHandler
 * @see BasicPythonErrorProcessHandler
 * @see ProcessPythonExecutorProperties
 * @see ProcessPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class BasicPythonInputProcessHandler implements ProcessHandler<String> {
    private final ProcessPythonExecutorProperties executorProperties;

    /**
     * Reads and processes the standard output stream of the specified {@link Process}.
     *
     * <p>Scans all output lines, detects the configured body marker, extracts the part
     * after the marker as a JSON string, and returns it.
     * Optionally logs all lines if enabled in {@link ProcessPythonExecutorProperties}.
     *
     * @param process the non-{@code null} {@link Process} whose standard output should be handled
     * @return the extracted JSON body string, or {@code null} if no marker was found
     * @throws ProcessReadingException if reading the standard output fails
     */
    @Override
    public String handle(Process process) {
        AtomicReference<String> result = new AtomicReference<>();
        try (BufferedReader bufferedReader = process.inputReader()) {
            bufferedReader.lines().forEach(line -> {
                String resultAppearance = executorProperties.resultAppearance();
                if (resultAppearance != null
                        && !resultAppearance.isBlank()
                        && line.contains(resultAppearance)) {
                    String resultJson = line.replace(resultAppearance, "");
                    result.set(resultJson);
                }
                if (executorProperties.loggable()) {
                    log.info(line);
                }
            });
        } catch (IOException e) {
            throw new ProcessReadingException(e);
        }
        return result.get();
    }
}