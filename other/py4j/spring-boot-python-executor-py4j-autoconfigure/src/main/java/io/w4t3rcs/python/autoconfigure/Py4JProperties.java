package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.resolver.Py4JResolver;
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
    private boolean enabled = true;
    private String host = "localhost";
    private int port = 25333;
    private String pythonHost = "localhost";
    private int pythonPort = 25334;
    private int connectTimeout = 0;
    private int readTimeout = 0;
    private String authToken = "secret-token";
    private boolean loggable = true;
}
