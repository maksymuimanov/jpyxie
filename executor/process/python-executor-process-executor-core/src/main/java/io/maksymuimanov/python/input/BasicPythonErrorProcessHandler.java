package io.maksymuimanov.python.input;

import io.maksymuimanov.python.exception.ProcessReadingException;
import io.maksymuimanov.python.executor.ProcessPythonExecutor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Processes and handles the standard error stream of a given {@link Process}.
 *
 * <p>This {@link ProcessHandler} implementation reads the error output (stderr) of the process.
 * If the error stream contains any non-blank content, the message is:
 * <ul>
 *     <li>Logged using SLF4J at <code>ERROR</code> level.</li>
 *     <li>Followed by throwing a {@link ProcessReadingException} containing the error details.</li>
 * </ul>
 * If the error stream is empty, the method completes successfully and returns {@code null}.
 *
 * <p><b>Error handling:</b>
 * <ul>
 *     <li>If an {@link IOException} occurs while reading the error stream,
 *         it is wrapped and rethrown as a {@link ProcessReadingException}.</li>
 *     <li>If the error stream contains data, execution is aborted by throwing
 *         a {@link ProcessReadingException}.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * Process process = new ProcessBuilder("python", "script.py").start();
 * new ErrorProcessHandler().handle(process);
 * }</pre>
 *
 * @see ProcessHandler
 * @see BasicPythonInputProcessHandler
 * @see ProcessPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
public class BasicPythonErrorProcessHandler implements ProcessHandler<Void> {
    /**
     * Reads and processes the standard error stream of the specified {@link Process}.
     *
     * <p>If the error stream contains non-blank content, logs the content at
     * <code>ERROR</code> level and throws a {@link ProcessReadingException}.
     * If no error content is present, returns {@code null}.
     *
     * @param process the non-{@code null} {@link Process} whose error output should be handled
     * @return {@code null} if no error output is present
     * @throws ProcessReadingException if reading the error stream fails or if the stream contains errors
     */
    @Override
    @Nullable
    public Void handle(Process process) {
        try (BufferedReader bufferedReader = process.errorReader()) {
            String errorMessage = bufferedReader.lines().collect(Collectors.joining());
            if (!errorMessage.isBlank()) {
                log.error(errorMessage);
                throw new ProcessReadingException(errorMessage);
            }
        } catch (IOException e) {
            throw new ProcessReadingException(e);
        }
        return null;
    }
}