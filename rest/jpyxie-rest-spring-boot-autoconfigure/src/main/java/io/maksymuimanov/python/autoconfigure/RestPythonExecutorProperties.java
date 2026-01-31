package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.RestPythonExecutor;
import io.maksymuimanov.python.http.BasicPythonServerRequestSender;
import io.maksymuimanov.python.library.RestPipManager;
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
    /**
     * Base host URL of the REST Python executor service.
     */
    private String host = BasicPythonServerRequestSender.DEFAULT_HOST;
    /**
     * Port number used to connect to the REST Python executor service.
     */
    private int port = BasicPythonServerRequestSender.DEFAULT_PORT;
    /**
     * Authentication token for securing REST requests to the Python executor.
     */
    private String token = BasicPythonServerRequestSender.DEFAULT_TOKEN;
    private String scriptEndpoint = RestPythonExecutor.DEFAULT_ENDPOINT;
    private String executeUri = this.host + ":" + this.port + this.scriptEndpoint;
    private String pipEndpoint = RestPipManager.DEFAULT_ENDPOINT;
    private String pipUri = this.host + ":" + this.port + this.pipEndpoint;
}
