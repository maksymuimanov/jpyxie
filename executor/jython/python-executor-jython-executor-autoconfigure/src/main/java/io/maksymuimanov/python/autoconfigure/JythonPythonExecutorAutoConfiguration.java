package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.JythonPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.lifecycle.JythonInitializer;
import org.python.util.PythonInterpreter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.util.function.Supplier;

@AutoConfiguration
@EnableConfigurationProperties(JythonProperties.class)
@ConditionalOnBooleanProperty(name = "spring.python.executor.jython.enabled", matchIfMissing = true)
public class JythonPythonExecutorAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(JythonInitializer.class)
    public JythonInitializer jepInitializer(JythonProperties properties) {
        return new JythonInitializer(properties.getPythonHome(), properties.isImportSite());
    }

    @Lazy
    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean(PythonInterpreter.class)
    public Supplier<PythonInterpreter> jepInterpreterSupplier() {
        return PythonInterpreter::new;
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor jythonPythonExecutor(Supplier<PythonInterpreter> jythonInterpreterSupplier, JythonProperties properties) {
        return new JythonPythonExecutor(jythonInterpreterSupplier, properties.getResultAppearance());
    }
}