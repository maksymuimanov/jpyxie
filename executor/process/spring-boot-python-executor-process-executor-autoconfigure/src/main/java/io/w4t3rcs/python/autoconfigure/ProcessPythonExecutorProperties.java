package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.executor.ProcessPythonExecutor;
import io.w4t3rcs.python.executor.PythonExecutor;
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
    private String startCommand = "python3";
    private boolean loggable = true;
    private String resultAppearance = "r4java";
}
