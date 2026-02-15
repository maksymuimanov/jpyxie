package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.interpreter.PythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.interpreter.ThreadLocalPythonInterpreterProvider;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInterpreterProviderFinalizer;
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
