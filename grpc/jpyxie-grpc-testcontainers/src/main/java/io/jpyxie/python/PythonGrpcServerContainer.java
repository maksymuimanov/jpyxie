package io.jpyxie.python;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

/**
 * Base Testcontainers container for gRPC Python server.
 * <p>
 * Supports configuration of environment variables related to authentication,
 * additional imports, and result formatting via fluent API.
 * </p>
 * The container exposes the standard gRPC port {@code 50051} and waits
 * for both the port availability and a specific log message indicating
 * successful startup.
 * <p>
 * Default token: {@value #DEFAULT_TOKEN}
 * Default additional imports delimiter: {@value #DEFAULT_ADDITIONAL_IMPORTS_DELIMITER}
 * </p>
 *
 * <p>Environment variables used:</p>
 * <ul>
 *   <li>{@value #PYTHON_SERVER_DEFAULT_PORT} - port for connection</li>
 *   <li>{@value #PYTHON_SERVER_TOKEN_ENV} - token for authentication</li>
 *   <li>{@value #PYTHON_ADDITIONAL_IMPORTS_ENV} - additional Python imports as a delimiter-separated string</li>
 *   <li>{@value #PYTHON_ADDITIONAL_IMPORTS_DELIMITER_ENV} - delimiter used in additional imports</li>
 *   <li>{@value #PYTHON_RESULT_APPEARANCE_ENV} - controls Python result appearance/formatting</li>
 *   <li>{@value #PYTHON_SERVER_THREAD_POOL_MAX_WORKERS_ENV} - thread pool max workers</li>
 * </ul>
 *
 * @see GenericContainer
 * @author w4t3rcs
 * @since 1.0.0
 */
public class PythonGrpcServerContainer extends GenericContainer<PythonGrpcServerContainer> {
    private static final String GRPC_SERVER_RUNNING_MESSAGE = ".*gRPC server running.+";
    private static final DockerImageName DOCKER_IMAGE_NAME = DockerImageName.parse("w4t3rcs/spring-boot-python-executor-python-grpc-server");
    private static final int PYTHON_SERVER_DEFAULT_PORT = 50051;
    public static final String PYTHON_SERVER_TOKEN_ENV = "PYTHON_SERVER_TOKEN";
    public static final String PYTHON_ADDITIONAL_IMPORTS_ENV = "PYTHON_ADDITIONAL_IMPORTS";
    public static final String PYTHON_ADDITIONAL_IMPORTS_DELIMITER_ENV = "PYTHON_ADDITIONAL_IMPORTS_DELIMITER";
    public static final String PYTHON_RESULT_APPEARANCE_ENV = "PYTHON_RESULT_APPEARANCE";
    public static final String PYTHON_SERVER_THREAD_POOL_MAX_WORKERS_ENV = "PYTHON_SERVER_THREAD_POOL_MAX_WORKERS";
    protected static final String DEFAULT_TOKEN = "secret";
    protected static final String DEFAULT_ADDITIONAL_IMPORTS_DELIMITER = ",";
    private String token = DEFAULT_TOKEN;

    /**
     * Creates a new {@link PythonGrpcServerContainer} instance with the given Docker image name.
     * <p>
     * The provided image name is {@code w4t3rcs/spring-boot-python-executor-python-grpc-server:latest}.
     */
    public PythonGrpcServerContainer() {
        this("w4t3rcs/spring-boot-python-executor-python-grpc-server:latest");
    }

    /**
     * Creates a new {@link PythonGrpcServerContainer} instance with the given Docker image name.
     * <p>
     * The provided image name must be compatible with
     * {@code w4t3rcs/spring-boot-python-executor-python-grpc-server}.
     *
     * @param image non-null Docker image name string, must be compatible with the expected base image
     */
    public PythonGrpcServerContainer(String image) {
        this(DockerImageName.parse(image));
    }

    /**
     * Creates a new {@link PythonGrpcServerContainer} instance with the given {@link DockerImageName}.
     * <p>
     * The provided Docker image must be compatible with
     * {@code w4t3rcs/spring-boot-python-executor-python-grpc-server}.
     * <p>
     * The container exposes port {@value #PYTHON_SERVER_DEFAULT_PORT} and waits
     * for the port to be listening as well as the log message defined by
     * {@value #GRPC_SERVER_RUNNING_MESSAGE} before considering itself started.
     * The startup timeout is 5 minutes.
     *
     * @param dockerImageName non-null {@link DockerImageName} instance, must be compatible with the expected base image
     */
    public PythonGrpcServerContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        this.withToken(DEFAULT_TOKEN);
        dockerImageName.assertCompatibleWith(DOCKER_IMAGE_NAME);
        this.addExposedPort(PYTHON_SERVER_DEFAULT_PORT);
        this.waitingFor(
                new WaitAllStrategy()
                        .withStartupTimeout(Duration.ofMinutes(5))
                        .withStrategy(Wait.forListeningPort())
                        .withStrategy(Wait.forLogMessage(GRPC_SERVER_RUNNING_MESSAGE, 1)));
    }

    /**
     * Sets the authentication token for the Python server container and corresponding environment variable.
     *
     * @param token token to use
     * @return this container instance for chaining
     */
    public PythonGrpcServerContainer withToken(String token) {
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
    public PythonGrpcServerContainer withAdditionalImports(String[] imports) {
        return this.withAdditionalImports(imports, DEFAULT_ADDITIONAL_IMPORTS_DELIMITER);
    }

    /**
     * Sets additional Python imports joined by a custom delimiter.
     *
     * @param imports array of import strings
     * @param delimiter delimiter string to join imports
     * @return this container instance for chaining
     */
    public PythonGrpcServerContainer withAdditionalImports(String[] imports, String delimiter) {
        this.withEnv(PYTHON_ADDITIONAL_IMPORTS_ENV, String.join(delimiter, imports));
        return this.self();
    }

    /**
     * Sets the delimiter to use for additional Python imports.
     *
     * @param importsDelimiter delimiter string
     * @return this container instance for chaining
     */
    public PythonGrpcServerContainer withAdditionalImportsDelimiter(String importsDelimiter) {
        this.withEnv(PYTHON_ADDITIONAL_IMPORTS_DELIMITER_ENV, importsDelimiter);
        return this.self();
    }

    /**
     * Sets the result appearance option for the Python server.
     *
     * @param resultAppearance string defining result appearance format
     * @return this container instance for chaining
     */
    public PythonGrpcServerContainer withResultAppearance(String resultAppearance) {
        this.withEnv(PYTHON_RESULT_APPEARANCE_ENV, resultAppearance);
        return this.self();
    }

    /**
     * Sets the maximum number of workers for the thread pool in the Python gRPC server.
     * <p>
     * This value is passed as an environment variable
     * {@value #PYTHON_SERVER_THREAD_POOL_MAX_WORKERS_ENV} to the container.
     *
     * @param maxWorkers non-negative integer specifying maximum thread pool workers, zero or negative values are accepted but their effect depends on server implementation
     * @return this container instance for chaining
     */
    public PythonGrpcServerContainer withThreadPoolMaxWorkers(int maxWorkers) {
        this.withEnv(PYTHON_SERVER_THREAD_POOL_MAX_WORKERS_ENV, String.valueOf(maxWorkers));
        return this.self();
    }

    /**
     * Returns the currently configured token.
     *
     * @return token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the server URL in the form {@code host:port} where {@code host}
     * is the container host and {@code port} is the mapped port {@value #PYTHON_SERVER_DEFAULT_PORT}.
     * <p>
     * This method should only be called after the container has been started.
     *
     * @return non-null, non-empty string representing the server URL, e.g. {@code localhost:50051}
     */
    public String getServerUrl() {
        return this.getHost() + ":" + this.getMappedPort(PYTHON_SERVER_DEFAULT_PORT);
    }
}