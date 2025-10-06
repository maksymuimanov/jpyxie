package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.executor.PythonExecutor;
import io.w4t3rcs.python.executor.RestPythonExecutor;
import lombok.Getter;
import lombok.Setter;
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
 * @see PythonExecutor
 * @see RestPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.executor.rest")
public class RestPythonExecutorProperties {
    private String host = "http://localhost";
    private int port = 8000;
    private String token;
    private String uri = host + ":" + port + "/script";
}
