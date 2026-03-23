package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInterpreterProviderFinalizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class PythonInterpreterAutoConfiguration {
    @Bean
    @ConditionalOnBean(PythonInterpreterProvider.class)
    @ConditionalOnMissingBean(PythonInterpreterProviderFinalizer.class)
    public PythonFinalizer pythonInterpreterProviderFinalizer(PythonInterpreterProvider<?> interpreterProvider) {
        return new PythonInterpreterProviderFinalizer(interpreterProvider);
    }
}
