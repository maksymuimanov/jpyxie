package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.file.PythonFileReader;
import io.maksymuimanov.python.processor.BasicPythonProcessor;
import io.maksymuimanov.python.processor.PythonProcessor;
import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguration class for {@link PythonProcessor}.
 * <p>This configuration defines the core infrastructure by declaring a {@link PythonProcessor} bean.</p>
 *
 * <p>If no other {@link PythonProcessor} bean is present in the Spring context,
 * it creates a {@link BasicPythonProcessor} instance wired with the required dependencies.</p>
 *
 * @see PythonProcessor
 * @see BasicPythonProcessor
 * @see PythonFileReader
 * @see PythonExecutor
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration(after = {PythonFileAutoConfiguration.class, PythonResolverAutoConfiguration.class})
public class PythonProcessorAutoConfiguration {
    /**
     * Creates a default {@link BasicPythonProcessor} bean.
     *
     * @param pythonFileReader non-null {@link PythonFileReader} instance to handle Python file operations.
     * @param pythonExecutor non-null {@link PythonExecutor} instance to execute Python code.
     * @param pythonResolverHolder non-null {@link PythonResolverHolder} instance to resolve Python-related parameters.
     * @return a non-null {@link PythonProcessor} implementation.
     */
    @Bean
    @ConditionalOnMissingBean(PythonProcessor.class)
    public PythonProcessor basicPythonProcessor(PythonFileReader pythonFileReader,
                                                PythonExecutor pythonExecutor,
                                                PythonResolverHolder pythonResolverHolder) {
        return new BasicPythonProcessor(pythonFileReader, pythonExecutor, pythonResolverHolder);
    }
}
