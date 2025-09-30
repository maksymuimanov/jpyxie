package io.w4t3rcs.python.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.w4t3rcs.python.connection.PythonServerConnectionDetails;
import io.w4t3rcs.python.executor.PythonExecutor;
import io.w4t3rcs.python.executor.RestPythonExecutor;
import io.w4t3rcs.python.properties.RestPythonExecutorProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.http.HttpClient;

/**
 * Central Spring Boot configuration for creating and wiring {@link PythonExecutor} beans.
 *
 * <p>This class declares {@link Bean} methods for remote execution via REST API using {@link RestPythonExecutor}
 *
 * @see PythonExecutor
 * @see RestPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(RestPythonExecutorProperties.class)
@PropertySource("classpath:python-rest-default.properties")
public class RestPythonExecutorAutoConfiguration {
    /**
     * Creates a {@link RestPythonExecutor} bean for executing Python scripts via REST API.
     *
     * <p>Activated when:
     * <ul>
     *   <li>{@code spring.python.executor.type=rest}</li>
     *   <li>No other {@link PythonExecutor} bean is present in the context</li>
     * </ul>
     *
     * @param connectionDetails non-null {@link PythonServerConnectionDetails} for REST server connection
     * @param objectMapper non-null {@link ObjectMapper} for JSON serialization/deserialization
     * @param restPythonServerHttpClient non-null {@link HttpClient} for HTTP communication with the Python server
     * @return never {@code null}, fully initialized {@link RestPythonExecutor} instance
     */
    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor restPythonExecutor(PythonServerConnectionDetails connectionDetails,
                                             ObjectMapper objectMapper,
                                             @Qualifier("restPythonServerHttpClient") HttpClient restPythonServerHttpClient) {
        return new RestPythonExecutor(connectionDetails, objectMapper, restPythonServerHttpClient);
    }

    /**
     * Creates a default {@link HttpClient} bean for REST-based Python execution.
     *
     * <p>Activated when:
     * <ul>
     *   <li>{@code spring.python.executor.type=rest}</li>
     * </ul>
     *
     * @return never {@code null}, new {@link HttpClient} instance
     */
    @Bean
    public HttpClient restPythonServerHttpClient() {
        return HttpClient.newHttpClient();
    }

    /**
     * Creates {@link PythonServerConnectionDetails} for REST Python execution from
     * {@link RestPythonExecutorProperties}.
     *
     * <p>Activated when:
     * <ul>
     *   <li>{@code spring.python.executor.type=rest}</li>
     *   <li>No other {@link PythonServerConnectionDetails} bean is present</li>
     * </ul>
     *
     * @param properties non-null {@link RestPythonExecutorProperties} containing REST configuration
     * @return never {@code null}, immutable connection details instance
     */
    @Bean
    @ConditionalOnMissingBean(PythonServerConnectionDetails.class)
    public PythonServerConnectionDetails restConnectionDetails(RestPythonExecutorProperties properties) {
        return PythonServerConnectionDetails.of(properties.token(), properties.uri());
    }
}