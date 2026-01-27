package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.bind.RestPythonDeserializer;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.RestPythonExecutor;
import io.maksymuimanov.python.executor.RestPythonResponse;
import io.maksymuimanov.python.http.BasicPythonServerRequestSender;
import io.maksymuimanov.python.http.PythonServerRequestSender;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.library.RestPipManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.boot.jackson.JsonMixinModule;
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

    /**
     * Creates {@link RestPythonServerConnectionDetails} for REST Python execution from
     * {@link RestPythonExecutorProperties}.
     *
     * <p>Activated when:
     * <ul>
     *   <li>No other {@link RestPythonServerConnectionDetails} bean is present</li>
     * </ul>
     *
     * @param properties non-null {@link RestPythonExecutorProperties} containing REST configuration
     * @return never {@code null}, immutable connection details instance
     */
    @Bean
    @ConditionalOnMissingBean(RestPythonServerConnectionDetails.class)
    public RestPythonServerConnectionDetails restConnectionDetails(RestPythonExecutorProperties properties) {
        return RestPythonServerConnectionDetails.of(properties.getToken(), properties.getExecuteUri(), properties.getPipUri());
    }

    @Bean
    public ObjectMapper pythonObjectMapper() {
        return JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .addModule(new JavaTimeModule())
                .addModule(new JsonComponentModule())
                .addModule(new JsonMixinModule())
                .build();
    }

    @Bean
    public PythonDeserializer<RestPythonResponse> restPythonDeserializer(@Qualifier("pythonObjectMapper") ObjectMapper objectMapper) {
        return new RestPythonDeserializer(objectMapper);
    }

    /**
     * Creates a default {@link HttpClient} bean for REST-based Python execution.
     *
     * @return never {@code null}, new {@link HttpClient} instance
     */
    @Bean
    public HttpClient pythonServerClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(PythonServerRequestSender.class)
    public PythonServerRequestSender pythonServerRequestSender(@Qualifier("pythonServerClient") HttpClient restPythonServerHttpClient) {
        return new BasicPythonServerRequestSender(restPythonServerHttpClient);
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor restPythonExecutor(PythonDeserializer<RestPythonResponse> pythonDeserializer,
                                             RestPythonServerConnectionDetails connectionDetails,
                                             PythonServerRequestSender requestSender,
                                             @Qualifier("pythonObjectMapper") ObjectMapper objectMapper) {
        return new RestPythonExecutor(pythonDeserializer, connectionDetails.getExecuteUri(), connectionDetails.getToken(), requestSender, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(PipManager.class)
    public PipManager pipManager(RestPythonServerConnectionDetails connectionDetails,
                                 PythonServerRequestSender requestSender,
                                 ObjectMapper objectMapper) {
        return new RestPipManager(connectionDetails.getExecuteUri(), connectionDetails.getToken(), requestSender, objectMapper);
    }
}