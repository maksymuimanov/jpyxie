package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultFieldNameProvider;
import io.maksymuimanov.python.executor.RestPythonExecutor;
import io.maksymuimanov.python.http.BasicPythonRestServerHttpRequestSender;
import io.maksymuimanov.python.http.PythonRestServerHttpRequestSender;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.library.RestPipManager;
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
@AutoConfiguration(beforeName = RestPythonExecutorAutoConfiguration.MAIN_PIP_AUTO_CONFIGURATION_REFERENCE)
@EnableConfigurationProperties(RestPythonExecutorProperties.class)
public class RestPythonExecutorAutoConfiguration {
    protected static final String MAIN_PIP_AUTO_CONFIGURATION_REFERENCE = "io.maksymuimanov.python.autoconfigure.PipAutoConfiguration";

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor restPythonExecutor(PythonResultFieldNameProvider resultFieldNameProvider,
                                             PythonRestServerConnectionDetails connectionDetails,
                                             PythonRestServerHttpRequestSender requestSender,
                                             ObjectMapper objectMapper) {
        return new RestPythonExecutor(resultFieldNameProvider, connectionDetails.getUri(), connectionDetails.getToken(), requestSender, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(PythonRestServerHttpRequestSender.class)
    public PythonRestServerHttpRequestSender pythonRestServerHttpRequestSender(@Qualifier("restPythonServerHttpClient") HttpClient restPythonServerHttpClient) {
        return new BasicPythonRestServerHttpRequestSender(restPythonServerHttpClient);
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
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
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

    @Bean
    @ConditionalOnMissingBean(PipManager.class)
    public PipManager pipManager(PythonRestServerConnectionDetails connectionDetails,
                                 PythonRestServerHttpRequestSender requestSender,
                                 ObjectMapper objectMapper) {
        return new RestPipManager(connectionDetails.getUri(), connectionDetails.getToken(), requestSender, objectMapper);
    }
}