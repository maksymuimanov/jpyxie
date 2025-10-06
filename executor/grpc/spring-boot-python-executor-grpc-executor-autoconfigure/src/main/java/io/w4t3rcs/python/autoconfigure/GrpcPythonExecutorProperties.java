package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.executor.GrpcPythonExecutor;
import io.w4t3rcs.python.executor.PythonExecutor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for selecting and configuring the {@link GrpcPythonExecutor}.
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     executor:
 *       grpc:
 *         host: localhost
 *         port: 50051
 *         token: secret
 * }</pre>
 * </p>
 *
 * @see PythonExecutor
 * @see GrpcPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.executor.grpc")
public class GrpcPythonExecutorProperties {
    private String host = "localhost";
    private int port = 50051;
    private String token;
    private String uri = this.host + ":" + this.port;
}
