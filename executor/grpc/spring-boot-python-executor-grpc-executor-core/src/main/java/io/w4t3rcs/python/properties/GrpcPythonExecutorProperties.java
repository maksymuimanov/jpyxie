package io.w4t3rcs.python.properties;

import io.w4t3rcs.python.executor.GrpcPythonExecutor;
import io.w4t3rcs.python.executor.PythonExecutor;
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
 * @param host gRPC service host, must not be null or blank
 * @param port gRPC service port
 * @param token authentication token
 * @param uri full URI to gRPC endpoint, must not be null or blank
 * @see PythonExecutor
 * @see GrpcPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@ConfigurationProperties("spring.python.executor.grpc")
public record GrpcPythonExecutorProperties(String host,
                                           int port,
                                           String token,
                                           String uri) {
}
