package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.interpreter.PythonInterpreterFactory;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.interpreter.ThreadLocalPythonInterpreterProvider;
import io.maksymuimanov.python.lifecycle.PythonFinalizer;
import io.maksymuimanov.python.lifecycle.PythonInterpreterProviderFinalizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class PythonInterpreterAutoConfiguration {
    @Bean
    @ConditionalOnBean(PythonInterpreterFactory.class)
    @ConditionalOnMissingBean(PythonInterpreterProvider.class)
    public PythonInterpreterProvider<?> pythonInterpreterProvider(PythonInterpreterFactory<?> interpreterFactory) {
        return new ThreadLocalPythonInterpreterProvider<>(interpreterFactory);
    }

    @Bean
    @ConditionalOnMissingBean(PythonInterpreterProviderFinalizer.class)
    public PythonFinalizer externalPythonLibraryFinalizer(PythonInterpreterProvider<?> interpreterProvider) {
        return new PythonInterpreterProviderFinalizer(interpreterProvider);
    }
}
