package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.executor.ProcessPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultFieldNameProvider;
import io.maksymuimanov.python.file.PythonFileReader;
import io.maksymuimanov.python.finisher.BasicPythonProcessFinisher;
import io.maksymuimanov.python.finisher.ProcessFinisher;
import io.maksymuimanov.python.output.BasicPythonErrorProcessHandler;
import io.maksymuimanov.python.output.BasicPythonOutputProcessHandler;
import io.maksymuimanov.python.output.ProcessErrorHandler;
import io.maksymuimanov.python.output.ProcessOutputHandler;
import io.maksymuimanov.python.starter.BasicPythonProcessStarter;
import io.maksymuimanov.python.starter.ProcessStarter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor processPythonExecutor(PythonResultFieldNameProvider resultFieldNameProvider,
                                                ProcessStarter processStarter,
                                                ProcessOutputHandler processOutputHandler,
                                                ProcessErrorHandler processErrorHandler,
                                                ObjectMapper objectMapper,
                                                ProcessFinisher processFinisher) {
        return new ProcessPythonExecutor(resultFieldNameProvider, processStarter, processOutputHandler, processErrorHandler, objectMapper, processFinisher);
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