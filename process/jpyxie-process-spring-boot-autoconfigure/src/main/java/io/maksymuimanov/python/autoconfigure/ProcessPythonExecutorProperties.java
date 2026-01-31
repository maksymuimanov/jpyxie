package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.BasicPythonOutputProcessHandler;
import io.maksymuimanov.python.executor.BasicPythonProcessStarter;
import io.maksymuimanov.python.executor.ProcessPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for selecting and configuring the {@link ProcessPythonExecutor}.
 *
 * <p>Properties are bound from the application configuration using the prefix
 * {@code spring.python.executor.process}.</p>
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     executor:
 *       process:
 *         start-command: python3
 *         loggable: true
 *         result-appearance: r4java
 * }</pre>
 * </p>
 *
 * @author w4t3rcs
 * @see PythonExecutor
 * @see ProcessPythonExecutor
 * @see ProcessPythonExecutorAutoConfiguration
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.executor.process")
public class ProcessPythonExecutorProperties {
    /**
     * The base command used to start the Python interpreter process.
     */
    private String startCommand = BasicPythonProcessStarter.DEFAULT_START_COMMAND;
    /**
     * Whether process execution details (input, output, errors) should be logged.
     */
    private boolean loggable = BasicPythonOutputProcessHandler.DEFAULT_LOGGABLE;
}
