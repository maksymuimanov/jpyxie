package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.JythonPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultFieldNameProvider;
import io.maksymuimanov.python.interpreter.JythonInterpreterFactory;
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
    public JythonInitializer jepInitializer(JythonProperties properties) {
        return new JythonInitializer(properties.getPythonHome(), properties.isImportSite());
    }

    @Bean
    @ConditionalOnMissingBean(JythonInterpreterFactory.class)
    public JythonInterpreterFactory jythonInterpreterFactory() {
        return new JythonInterpreterFactory();
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor jythonPythonExecutor(PythonResultFieldNameProvider resultFieldNameProvider, PythonInterpreterProvider<PythonInterpreter> jythonInterpreterProvider) {
        return new JythonPythonExecutor(resultFieldNameProvider, jythonInterpreterProvider);
    }
}