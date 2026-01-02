package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.bind.JythonPythonDeserializer;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.executor.JythonPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.interpreter.JythonInterpreterFactory;
import io.maksymuimanov.python.interpreter.PythonInterpreterFactory;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.lifecycle.JythonInitializer;
import org.python.util.PythonInterpreter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(JythonProperties.class)
@ConditionalOnBooleanProperty(name = "spring.python.executor.jython.enabled", matchIfMissing = true)
public class JythonPythonExecutorAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(JythonInitializer.class)
    public JythonInitializer jythonInitializer(JythonProperties properties) {
        return new JythonInitializer(properties.getPythonHome(), properties.isImportSite());
    }

    @Bean
    @ConditionalOnMissingBean(PythonDeserializer.class)
    public PythonDeserializer<PythonInterpreter> jythonPythonDeserializer() {
        return new JythonPythonDeserializer();
    }

    @Bean
    @ConditionalOnMissingBean(PythonInterpreterFactory.class)
    public PythonInterpreterFactory<PythonInterpreter> jythonInterpreterFactory() {
        return new JythonInterpreterFactory();
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor jythonPythonExecutor(PythonDeserializer<PythonInterpreter> jythonPythonDeserializer, PythonInterpreterProvider<PythonInterpreter> jythonInterpreterProvider) {
        return new JythonPythonExecutor(jythonPythonDeserializer, jythonInterpreterProvider);
    }
}