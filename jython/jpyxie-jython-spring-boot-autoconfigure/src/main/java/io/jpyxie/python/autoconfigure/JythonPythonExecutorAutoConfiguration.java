package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.bind.JythonPythonDeserializer;
import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.executor.JythonPythonExecutor;
import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.interpreter.JythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.lifecycle.JythonInitializer;
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