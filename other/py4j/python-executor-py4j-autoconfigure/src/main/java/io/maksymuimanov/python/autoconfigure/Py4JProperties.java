package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.resolver.Py4JResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the Py4J integration.
 * <p>
 * These properties define how the Py4J gateway is configured for communication between Java and Python processes.
 * The configuration is loaded from the application configuration files
 * (e.g. {@code application.yml} or {@code application.properties}) using the prefix {@code spring.python.py4j}.
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     py4j:
 *       enabled: true
 *       host: localhost
 *       port: 25333
 *       python-host: localhost
 *       python-port: 25334
 *       connect-timeout: 0
 *       read-timeout: 0
 *       auth-token: secret-token
 *       loggable: true
 * }</pre>
 *
 * @author w4t3rcs
 * @see Py4JResolver
 * @see <a href="https://www.py4j.org/">Py4J Documentation</a>
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.py4j")
public class Py4JProperties {
    /**
     * Whether the Py4J autoconfiguration is enabled.
     */
    private boolean enabled = true;
    /**
     * Host address of the Java-side Py4J GatewayServer.
     */
    private String host = "localhost";
    /**
     * Port number on which the Java-side Py4J GatewayServer listens.
     */
    private int port = 25333;
    /**
     * Host address of the Python-side Py4J client or callback server.
     */
    private String pythonHost = "localhost";
    /**
     * Port number for the Python-side Py4J callback server.
     */
    private int pythonPort = 25334;
    /**
     * Connection timeout (in milliseconds) when establishing the Py4J gateway connection. A value of 0 means no timeout.
     */
    private int connectTimeout = 0;
    /**
     * Read timeout (in milliseconds) for communication through the Py4J gateway. A value of 0 means no timeout.
     */
    private int readTimeout = 0;
    /**
     * Authentication token used to secure the Py4J connection between Java and Python processes.
     */
    private String authToken;
    /**
     * Whether communication details and errors between Java and Python via Py4J should be logged.
     */
    private boolean loggable = true;
}
