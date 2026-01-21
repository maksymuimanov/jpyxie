package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.GrpcPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
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
    /**
     * Host address of the gRPC Python executor service.
     */
    private String host = "localhost";
    /**
     * Port number used to connect to the gRPC Python executor service.
     */
    private int port = 50051;
    /**
     * Authentication token required for secure gRPC communication.
     */
    private String token;
    /**
     * Full URI composed from host and port.
     */
    private String uri = this.host + ":" + this.port;
}
