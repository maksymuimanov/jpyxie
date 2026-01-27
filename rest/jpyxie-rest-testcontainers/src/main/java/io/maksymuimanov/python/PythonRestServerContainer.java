package io.maksymuimanov.python;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

/**
 * Base Testcontainers container for Python REST server.
 * <p>
 * Supports configuration of environment variables related to authentication,
 * additional imports, and result formatting via fluent API.
 * </p>
 * <p>This container provides an HTTP interface for executing Python scripts in a
 * controlled environment. It exposes a single REST endpoint at the
 * {@code /script} path on the configured port. By default, the container runs
 * on port {@value #DEFAULT_PORT} and uses the official Docker image
 * {@code w4t3rcs/spring-boot-python-executor-python-rest-server}.</p>
 * <p>
 * Default token: {@value #DEFAULT_TOKEN}
 * Default additional imports delimiter: {@value #DEFAULT_ADDITIONAL_IMPORTS_DELIMITER}
 * </p>
 *
 * <p>Environment variables used:</p>
 * <ul>
 *   <li>{@value #DEFAULT_PORT} - port for connection</li>
 *   <li>{@value #PYTHON_SERVER_TOKEN_ENV} - token for authentication</li>
 *   <li>{@value #PYTHON_ADDITIONAL_IMPORTS_ENV} - additional Python imports as a delimiter-separated string</li>
 *   <li>{@value #PYTHON_ADDITIONAL_IMPORTS_DELIMITER_ENV} - delimiter used in additional imports</li>
 *   <li>{@value #PYTHON_RESULT_APPEARANCE_ENV} - controls Python result appearance/formatting</li>
 * </ul>
 *
 * @see GenericContainer
 * @author w4t3rcs
 * @since 1.0.0
 */
public class PythonRestServerContainer extends GenericContainer<PythonRestServerContainer> {
    public static final String DOCKER_IMAGE_NAME_STRING = "w4t3rcs/spring-boot-python-executor-python-rest-server:latest";
    private static final DockerImageName DOCKER_IMAGE_NAME = DockerImageName.parse(DOCKER_IMAGE_NAME_STRING);
    public static final String PYTHON_SERVER_TOKEN_ENV = "PYTHON_SERVER_TOKEN";
    public static final String PYTHON_ADDITIONAL_IMPORTS_ENV = "PYTHON_ADDITIONAL_IMPORTS";
    public static final String PYTHON_ADDITIONAL_IMPORTS_DELIMITER_ENV = "PYTHON_ADDITIONAL_IMPORTS_DELIMITER";
    public static final String PYTHON_RESULT_APPEARANCE_ENV = "PYTHON_RESULT_APPEARANCE";
    protected static final int DEFAULT_PORT = 8000;
    protected static final String DEFAULT_EXECUTE_ENDPOINT = "/execute";
    protected static final String DEFAULT_PIP_ENDPOINT = "/pip";
    protected static final String DEFAULT_TOKEN = "secret";
    protected static final String DEFAULT_ADDITIONAL_IMPORTS_DELIMITER = ",";
    private String executeEndpoint = DEFAULT_EXECUTE_ENDPOINT;
    private String pipEndpoint = DEFAULT_PIP_ENDPOINT;
    private String token = DEFAULT_TOKEN;

    /**
     * Creates a new {@link PythonRestServerContainer} instance using a Docker image name string.
     * Docker image to use is {@link #DOCKER_IMAGE_NAME_STRING}
     */
    public PythonRestServerContainer() {
        this(DOCKER_IMAGE_NAME_STRING);
    }

    /**
     * Creates a new {@link PythonRestServerContainer} instance using a Docker image name string.
     *
     * @param image non-null string representation of the Docker image to use, must be compatible with {@code w4t3rcs/spring-boot-python-executor-python-rest-server}
     */
    public PythonRestServerContainer(String image) {
        this(DockerImageName.parse(image));
    }

    /**
     * Creates a new {@link PythonRestServerContainer} instance using a {@link DockerImageName}.
     *
     * <p>The image name is validated for compatibility with {@code w4t3rcs/spring-boot-python-executor-python-rest-server} before the
     * container is configured.</p>
     *
     * @param dockerImageName non-null {@link DockerImageName} instance
     */
    public PythonRestServerContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        this.withExecuteEndpoint(DEFAULT_EXECUTE_ENDPOINT);
        this.withPipEndpoint(DEFAULT_PIP_ENDPOINT);
        this.withToken(DEFAULT_TOKEN);
        dockerImageName.assertCompatibleWith(DOCKER_IMAGE_NAME);
        this.addExposedPort(DEFAULT_PORT);
        this.waitingFor(
                new WaitAllStrategy()
                        .withStartupTimeout(Duration.ofMinutes(5))
                        .withStrategy(Wait.forListeningPort()));
    }

    public PythonRestServerContainer withExecuteEndpoint(String endpoint) {
        this.executeEndpoint = endpoint;
        return this.self();
    }

    public PythonRestServerContainer withPipEndpoint(String endpoint) {
        this.pipEndpoint = endpoint;
        return this.self();
    }

    /**
     * Sets the authentication token for the Python server container and corresponding environment variable.
     *
     * @param token token to use
     * @return this container instance for chaining
     */
    public PythonRestServerContainer withToken(String token) {
        this.withEnv(PYTHON_SERVER_TOKEN_ENV, token);
        this.token = token;
        return this.self();
    }

    /**
     * Sets additional Python imports with default delimiter (",").
     *
     * @param imports array of import strings
     * @return this container instance for chaining
     */
    public PythonRestServerContainer withAdditionalImports(String[] imports) {
        return this.withAdditionalImports(imports, DEFAULT_ADDITIONAL_IMPORTS_DELIMITER);
    }

    /**
     * Sets additional Python imports joined by a custom delimiter.
     *
     * @param imports array of import strings
     * @param delimiter delimiter string to join imports
     * @return this container instance for chaining
     */
    public PythonRestServerContainer withAdditionalImports(String[] imports, String delimiter) {
        this.withEnv(PYTHON_ADDITIONAL_IMPORTS_ENV, String.join(delimiter, imports));
        return this.self();
    }

    /**
     * Sets the delimiter to use for additional Python imports.
     *
     * @param importsDelimiter delimiter string
     * @return this container instance for chaining
     */
    public PythonRestServerContainer withAdditionalImportsDelimiter(String importsDelimiter) {
        this.withEnv(PYTHON_ADDITIONAL_IMPORTS_DELIMITER_ENV, importsDelimiter);
        return this.self();
    }

    /**
     * Sets the result appearance option for the Python server.
     *
     * @param resultAppearance string defining result appearance format
     * @return this container instance for chaining
     */
    public PythonRestServerContainer withResultAppearance(String resultAppearance) {
        this.withEnv(PYTHON_RESULT_APPEARANCE_ENV, resultAppearance);
        return this.self();
    }

    public String getExecuteUrl() {
        return this.getHost() + ":" + this.getMappedPort(DEFAULT_PORT) + this.executeEndpoint;
    }

    public String getPipUrl() {
        return this.getHost() + ":" + this.getMappedPort(DEFAULT_PORT) + this.pipEndpoint;
    }

    /**
     * Returns the currently configured token.
     *
     * @return token string
     */
    public String getToken() {
        return token;
    }
}