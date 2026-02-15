package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.executor.RestPythonExecutor;
import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;

/**
 * Defines connection parameters required to establish a link with a Python server inside the {@link RestPythonExecutor}.
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
 * @see RestPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface RestPythonServerConnectionDetails extends ConnectionDetails {
    String getExecuteUri();

    String getPipUri();

    /**
     * Returns the token used for authenticating with the Python server.
     *
     * @return non-{@code null} token
     */
    String getToken();

    static RestPythonServerConnectionDetails of(String token, String executeUri, String pipUri) {
        return new RestPythonServerConnectionDetails() {
            @Override
            public String getExecuteUri() {
                return executeUri;
            }

            @Override
            public String getPipUri() {
                return pipUri;
            }

            @Override
            public String getToken() {
                return token;
            }
        };
    }
}