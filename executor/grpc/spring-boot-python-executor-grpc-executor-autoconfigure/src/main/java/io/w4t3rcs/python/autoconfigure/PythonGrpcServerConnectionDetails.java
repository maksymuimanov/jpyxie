package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.executor.GrpcPythonExecutor;
import io.w4t3rcs.python.executor.PythonExecutor;
import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;

/**
 * Defines connection parameters required to establish a link with a Python server inside the {@link GrpcPythonExecutor}.
 *
 * <p>This interface extends {@link ConnectionDetails} and provides
 * credentials and the target server URI in a structured manner. It is intended
 * for use in Spring Boot autoconfiguration and connection management components.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be immutable
 * and thread-safe. All returned values must be consistent throughout the lifecycle of the object.</p>
 *
 * @see ConnectionDetails
 * @see PythonExecutor
 * @see GrpcPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface PythonGrpcServerConnectionDetails extends ConnectionDetails {
    /**
     * Returns the token used for authenticating with the Python server.
     *
     * @return non-{@code null} token
     */
    String getToken();

    /**
     * Returns the URI of the target Python server.
     *
     * @return non-{@code null} server URI
     */
    String getUri();

    /**
     * Creates an immutable {@link PythonGrpcServerConnectionDetails} instance with the given parameters.
     *
     * <p>The returned instance is thread-safe and all values are stored as provided.
     * Null values are not allowed.</p>
     *
     * @param token non-{@code null} token
     * @param uri non-{@code null} URI of the Python server, including protocol and port if applicable
     * @return non-{@code null} {@link PythonGrpcServerConnectionDetails} instance
     */
    static PythonGrpcServerConnectionDetails of(String token, String uri) {
        return new PythonGrpcServerConnectionDetails() {
            @Override
            public String getToken() {
                return token;
            }

            @Override
            public String getUri() {
                return uri;
            }
        };
    }
}