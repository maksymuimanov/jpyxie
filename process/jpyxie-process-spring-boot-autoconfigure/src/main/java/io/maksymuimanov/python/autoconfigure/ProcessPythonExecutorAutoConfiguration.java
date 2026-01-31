package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.maksymuimanov.python.bind.ProcessPythonDeserializer;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.executor.*;
import io.maksymuimanov.python.file.PythonFileReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.boot.jackson.JsonMixinModule;
import org.springframework.context.annotation.Bean;

/**
 * Central Spring Boot configuration for creating and wiring {@link PythonExecutor} beans.
 *
 * <p>This class declares {@link Bean} methods for local process execution via {@link ProcessPythonExecutor}
 * for all supported execution types:
 *
 * @see PythonExecutor
 * @see ProcessPythonExecutor
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(ProcessPythonExecutorProperties.class)
public class ProcessPythonExecutorAutoConfiguration {
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
    @ConditionalOnMissingBean
    public PythonDeserializer<ProcessPythonResponse> processPythonDeserializer(@Qualifier("pythonObjectMapper") ObjectMapper objectMapper) {
        return new ProcessPythonDeserializer(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor processPythonExecutor(PythonDeserializer<ProcessPythonResponse> jsonPythonDeserializer,
                                                ProcessStarter processStarter,
                                                ProcessOutputHandler processOutputHandler,
                                                ProcessErrorHandler processErrorHandler,
                                                ProcessFinisher processFinisher) {
        return new ProcessPythonExecutor(jsonPythonDeserializer, processStarter, processOutputHandler, processErrorHandler, processFinisher);
    }

    /**
     * Creates the {@link ProcessStarter} bean for initializing and starting
     * local Python processes.
     *
     * <p>
     * The returned instance is based on {@link BasicPythonProcessStarter}, which uses the
     * provided {@link ProcessPythonExecutorProperties} and {@link PythonFileReader}
     * to configure and manage process startup.
     * </p>
     *
     * @param executorProperties non-null execution settings for Python processes
     * @return a non-null {@link ProcessStarter} implementation
     */
    @Bean
    @ConditionalOnMissingBean(ProcessStarter.class)
    public ProcessStarter processStarter(ProcessPythonExecutorProperties executorProperties) {
        return new BasicPythonProcessStarter(executorProperties.getStartCommand());
    }

    @Bean
    @ConditionalOnMissingBean(ProcessOutputHandler.class)
    public ProcessOutputHandler processOutputHandler(ProcessPythonExecutorProperties executorProperties) {
        return new BasicPythonOutputProcessHandler(executorProperties.isLoggable());
    }

    @Bean
    @ConditionalOnMissingBean(ProcessErrorHandler.class)
    public ProcessErrorHandler processErrorHandler() {
        return new BasicPythonErrorProcessHandler();
    }

    /**
     * Creates the {@link ProcessFinisher} bean responsible for cleanly
     * terminating and releasing resources associated with the Python process.
     *
     * <p>
     * The returned instance is based on {@link BasicPythonProcessFinisher}.
     * </p>
     *
     * @return a non-null {@link ProcessFinisher} implementation
     */
    @Bean
    @ConditionalOnMissingBean(ProcessFinisher.class)
    public ProcessFinisher processFinisher() {
        return new BasicPythonProcessFinisher();
    }
}