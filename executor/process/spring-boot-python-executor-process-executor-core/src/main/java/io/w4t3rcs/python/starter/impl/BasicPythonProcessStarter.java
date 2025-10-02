package io.w4t3rcs.python.starter.impl;

import io.w4t3rcs.python.exception.ProcessStartException;
import io.w4t3rcs.python.executor.ProcessPythonExecutor;
import io.w4t3rcs.python.file.PythonFileReader;
import io.w4t3rcs.python.properties.ProcessPythonExecutorProperties;
import io.w4t3rcs.python.script.PythonScript;
import io.w4t3rcs.python.starter.ProcessStarter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Starts Python processes from script files or inline Python code.
 *
 * <p>This {@link ProcessStarter} implementation constructs and launches a {@link Process}
 * using the configured Python start command from {@link ProcessPythonExecutorProperties}.
 * Depending on the input, it will either:
 * <ul>
 *     <li>Execute a Python script from a file path (if {@link PythonScript#isFile()} returns {@code true}).</li>
 *     <li>Execute inline Python code using the {@code -c} option.</li>
 * </ul>
 *
 * <p>If inline code contains double quotes, they are escaped by doubling them to ensure
 * proper command-line parsing.</p>
 *
 * <p><b>Execution order:</b> After starting the process via {@link ProcessBuilder#start()},
 * this implementation waits for its completion using {@link Process#waitFor()} before returning.
 * As a body, the returned process is always in a terminated state.</p>
 *
 * <p>Example usage:
 * <pre>{@code
 * ProcessStarter starter = new ProcessStarterImpl(executorProperties, pythonFileHandler);
 *
 * // Start from a Python file
 * Process fileProcess = starter.start("/path/to/script.py");
 *
 * // Start from inline code
 * Process inlineProcess = starter.start("print('Hello from inline Python')");
 * }</pre>
 *
 * @see ProcessStarter
 * @see ProcessPythonExecutorProperties
 * @see PythonFileReader
 * @see ProcessPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class BasicPythonProcessStarter implements ProcessStarter {
    private static final String COMMAND_HEADER = "-c";
    private final ProcessPythonExecutorProperties executorProperties;
    private final PythonFileReader pythonFileReader;

    /**
     * Starts a Python process from either a file or inline code.
     *
     * @param script non-{@code null} Python script, can be a file path or inline code
     * @return non-{@code null} terminated {@link Process} representing the executed script
     * @throws ProcessStartException if the process cannot be started or interrupted
     */
    @Override
    public Process start(PythonScript script) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String startCommand = executorProperties.startCommand();
            String scriptBody = script.toString();
            if (script.isFile()) {
                processBuilder.command(startCommand, pythonFileReader.getScriptPath(scriptBody).toString());
            } else {
                processBuilder.command(startCommand, COMMAND_HEADER, scriptBody.replace("\"", "\"\""));
            }

            log.info("Python script is going to be executed");
            Process process = processBuilder.start();
            process.waitFor();
            return process;
        } catch (Exception e) {
            throw new ProcessStartException(e);
        }
    }
}