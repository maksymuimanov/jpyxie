package io.maksymuimanov.python.input;

import io.maksymuimanov.python.exception.PythonProcessReadingException;
import io.maksymuimanov.python.executor.ProcessPythonExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Processes and handles the standard output stream of a given {@link Process}.
 *
 * <p>This {@link ProcessHandler} implementation reads the standard output (stdout) of the process,
 * detects and extracts the body value marked by a configured appearance string from
 * {@link BasicPythonInputProcessHandler#resultAppearance}, and returns it as a raw JSON string.
 *
 * <p>If {@link BasicPythonInputProcessHandler#loggable} is enabled, all output lines
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
 * @see ProcessPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class BasicPythonInputProcessHandler implements ProcessHandler<String> {
    private final String resultAppearance;
    private final boolean loggable;

    /**
     * Reads and processes the standard output stream of the specified {@link Process}.
     *
     * <p>Scans all output lines, detects the configured body marker, extracts the part
     * after the marker as a JSON string, and returns it.
     * Optionally logs all lines if enabled.
     *
     * @param process the non-{@code null} {@link Process} whose standard output should be handled
     * @return the extracted JSON body string, or {@code null} if no marker was found
     * @throws PythonProcessReadingException if reading the standard output fails
     */
    @Override
    @Nullable
    public String handle(Process process) {
        AtomicReference<String> result = new AtomicReference<>();
        try (BufferedReader bufferedReader = process.inputReader()) {
            bufferedReader.lines().forEach(line -> {
                String resultAppearance = this.resultAppearance;
                if (!resultAppearance.isBlank() && line.contains(resultAppearance)) {
                    String resultJson = line.replace(resultAppearance, "");
                    result.set(resultJson);
                }
                if (this.loggable) {
                    log.info(line);
                }
            });
        } catch (IOException e) {
            throw new PythonProcessReadingException(e);
        }
        return result.get();
    }
}