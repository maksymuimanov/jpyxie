package io.w4t3rcs.python.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.w4t3rcs.python.executor.PythonExecutor;
import io.w4t3rcs.python.executor.RestPythonExecutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

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
@AutoConfiguration
@EnableConfigurationProperties(RestPythonExecutorProperties.class)
public class RestPythonExecutorAutoConfiguration {
    /**
     * Creates a {@link RestPythonExecutor} bean for executing Python scripts via REST API.
     *
     * <p>Activated when:
     * <ul>
     *   <li>No other {@link PythonExecutor} bean is present in the context</li>
     * </ul>
     *
     * @param connectionDetails non-null {@link PythonRestServerConnectionDetails} for REST server connection
     * @param objectMapper non-null {@link ObjectMapper} for JSON serialization/deserialization
     * @param restPythonServerHttpClient non-null {@link HttpClient} for HTTP communication with the Python server
     * @return never {@code null}, fully initialized {@link RestPythonExecutor} instance
     */
    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor restPythonExecutor(PythonRestServerConnectionDetails connectionDetails,
                                             ObjectMapper objectMapper,
                                             @Qualifier("restPythonServerHttpClient") HttpClient restPythonServerHttpClient) {
        return new RestPythonExecutor(connectionDetails.getToken(), connectionDetails.getUri(), objectMapper, restPythonServerHttpClient);
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
     * Creates {@link PythonRestServerConnectionDetails} for REST Python execution from
     * {@link RestPythonExecutorProperties}.
     *
     * <p>Activated when:
     * <ul>
     *   <li>No other {@link PythonRestServerConnectionDetails} bean is present</li>
     * </ul>
     *
     * @param properties non-null {@link RestPythonExecutorProperties} containing REST configuration
     * @return never {@code null}, immutable connection details instance
     */
    @Bean
    @ConditionalOnMissingBean(PythonRestServerConnectionDetails.class)
    public PythonRestServerConnectionDetails restConnectionDetails(RestPythonExecutorProperties properties) {
        return PythonRestServerConnectionDetails.of(properties.getToken(), properties.getUri());
    }
}