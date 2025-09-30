package io.w4t3rcs.python.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.w4t3rcs.python.executor.ProcessPythonExecutor;
import io.w4t3rcs.python.executor.PythonExecutor;
import io.w4t3rcs.python.file.PythonFileHandler;
import io.w4t3rcs.python.finisher.ProcessFinisher;
import io.w4t3rcs.python.finisher.impl.BasicPythonProcessFinisher;
import io.w4t3rcs.python.input.ProcessHandler;
import io.w4t3rcs.python.input.impl.BasicPythonErrorProcessHandler;
import io.w4t3rcs.python.input.impl.BasicPythonInputProcessHandler;
import io.w4t3rcs.python.properties.ProcessPythonExecutorProperties;
import io.w4t3rcs.python.starter.ProcessStarter;
import io.w4t3rcs.python.starter.impl.BasicPythonProcessStarter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
@Configuration
@EnableConfigurationProperties(ProcessPythonExecutorProperties.class)
@PropertySource("classpath:python-process-default.properties")
public class ProcessPythonExecutorAutoConfiguration {
    /**
     * Creates a {@link ProcessPythonExecutor} bean for executing Python scripts locally via Process API.
     *
     * @param processStarter non-null {@link ProcessStarter} for launching Python processes
     * @param inputProcessHandler non-null {@link ProcessHandler} for handling process input
     * @param errorProcessHandler non-null {@link ProcessHandler} for handling process error output
     * @param objectMapper non-null {@link ObjectMapper} for JSON serialization/deserialization
     * @param processFinisher non-null {@link ProcessFinisher} for finalizing process execution
     * @return never {@code null}, fully initialized {@link ProcessPythonExecutor} instance
     */
    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor processPythonExecutor(ProcessStarter processStarter,
                                                ProcessHandler<String> inputProcessHandler,
                                                ProcessHandler<Void> errorProcessHandler,
                                                ObjectMapper objectMapper,
                                                ProcessFinisher processFinisher) {
        return new ProcessPythonExecutor(processStarter, inputProcessHandler, errorProcessHandler, objectMapper, processFinisher);
    }

    /**
     * Creates the {@link io.w4t3rcs.python.starter.ProcessStarter} bean for initializing and starting
     * local Python processes.
     *
     * <p>
     * The returned instance is based on {@link BasicPythonProcessStarter}, which uses the
     * provided {@link ProcessPythonExecutorProperties} and {@link PythonFileHandler}
     * to configure and manage process startup.
     * </p>
     *
     * @param executorProperties non-null execution settings for Python processes
     * @param pythonFileHandler non-null handler for managing Python files and scripts
     * @return a non-null {@link ProcessStarter} implementation
     */
    @Bean
    @ConditionalOnMissingBean(ProcessStarter.class)
    public ProcessStarter processStarter(ProcessPythonExecutorProperties executorProperties, PythonFileHandler pythonFileHandler) {
        return new BasicPythonProcessStarter(executorProperties, pythonFileHandler);
    }

    /**
     * Creates the {@link ProcessHandler} bean responsible for handling
     * standard input (stdin) communication with the Python process.
     *
     * <p>
     * The returned instance is based on {@link BasicPythonInputProcessHandler} and supports
     * passing string-based input to the running Python process.
     * </p>
     *
     * @param executorProperties non-null execution settings for Python processes
     * @return a non-null {@link ProcessHandler} implementation for input handling
     */
    @Bean
    @ConditionalOnMissingBean(BasicPythonInputProcessHandler.class)
    public ProcessHandler<String> inputProcessHandler(ProcessPythonExecutorProperties executorProperties) {
        return new BasicPythonInputProcessHandler(executorProperties);
    }

    /**
     * Creates the {@link ProcessHandler} bean responsible for handling
     * standard error (stderr) output from the Python process.
     *
     * <p>
     * The returned instance is based on {@link BasicPythonErrorProcessHandler} and ignores input,
     * using {@link Void} as the generic type.
     * </p>
     *
     * @return a non-null {@link ProcessHandler} implementation for error handling
     */
    @Bean
    @ConditionalOnMissingBean(BasicPythonErrorProcessHandler.class)
    public ProcessHandler<Void> errorProcessHandler() {
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