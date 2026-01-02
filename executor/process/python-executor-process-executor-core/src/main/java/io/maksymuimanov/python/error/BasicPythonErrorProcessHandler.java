package io.maksymuimanov.python.error;

import io.maksymuimanov.python.exception.PythonProcessReadingException;
import io.maksymuimanov.python.executor.ProcessPythonExecutor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Processes and handles the standard error stream of a given {@link Process}.
 *
 * <p>This {@link ProcessErrorHandler} implementation reads the error output (stderr) of the process.
 * If the error stream contains any non-blank content, the message is:
 * <ul>
 *     <li>Logged using SLF4J at <code>ERROR</code> level.</li>
 *     <li>Followed by throwing a {@link PythonProcessReadingException} containing the error details.</li>
 * </ul>
 * If the error stream is empty, the method completes successfully and returns {@code null}.
 *
 * <p><b>Error handling:</b>
 * <ul>
 *     <li>If an {@link IOException} occurs while reading the error stream,
 *         it is wrapped and rethrown as a {@link PythonProcessReadingException}.</li>
 *     <li>If the error stream contains data, execution is aborted by throwing
 *         a {@link PythonProcessReadingException}.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * Process process = new ProcessBuilder("python", "script.py").start();
 * new ErrorProcessHandler().handle(process);
 * }</pre>
 *
 * @see ProcessErrorHandler
 * @see ProcessPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
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