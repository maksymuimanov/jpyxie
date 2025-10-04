package io.w4t3rcs.python.properties;

import io.w4t3rcs.python.executor.ProcessPythonExecutor;
import io.w4t3rcs.python.executor.PythonExecutor;
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
 * @param startCommand the command to start Python interpreter, must not be null or blank
 * @param loggable flag indicating if output should be logged
 * @see PythonExecutor
 * @see ProcessPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@ConfigurationProperties("spring.python.executor.process")
public record ProcessPythonExecutorProperties(String startCommand,
                                              boolean loggable,
                                              String resultAppearance) {
}
