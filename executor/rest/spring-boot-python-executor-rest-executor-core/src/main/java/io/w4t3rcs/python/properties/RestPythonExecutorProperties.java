package io.w4t3rcs.python.properties;

import io.w4t3rcs.python.executor.PythonExecutor;
import io.w4t3rcs.python.executor.RestPythonExecutor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for selecting and configuring the {@link RestPythonExecutor}.
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     executor:
 *       rest:
 *         host: http://localhost
 *         port: 8000
 *         token: secret
 * }</pre>
 * </p>
 *
 * @param host REST service host, must not be null or blank
 * @param port REST service port
 * @param token authentication token
 * @param uri full URI to REST endpoint, must not be null or blank
 * @see PythonExecutor
 * @see RestPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@ConfigurationProperties("spring.python.executor.rest")
public record RestPythonExecutorProperties(String host, int port, String token, String uri) {
}
