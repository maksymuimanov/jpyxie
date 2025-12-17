package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.JepPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.interpreter.JepInterpreterFactory;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.lifecycle.JepFinalizer;
import io.maksymuimanov.python.lifecycle.JepInitializer;
import io.maksymuimanov.python.lifecycle.PythonFinalizer;
import io.maksymuimanov.python.lifecycle.PythonInitializer;
import jep.Interpreter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(JepProperties.class)
@ConditionalOnBooleanProperty(name = "spring.python.executor.jep.enabled", matchIfMissing = true)
public class JepPythonExecutorAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(JepInitializer.class)
    @ConditionalOnBooleanProperty(name = "spring.python.executor.jep.library.enabled", matchIfMissing = true)
    public PythonInitializer jepInitializer(PipManager pipManager, JepProperties properties) {
        return new JepInitializer(pipManager, properties.getLibrary().getInstall());
    }

    @Bean
    @ConditionalOnMissingBean(JepInterpreterFactory.class)
    public JepInterpreterFactory jepInterpreterFactory(JepProperties properties) {
        return new JepInterpreterFactory(properties.getInterpreterType());
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor jepPythonExecutor(PythonInterpreterProvider<Interpreter> jepInterpreterProvider, JepProperties properties) {
        return new JepPythonExecutor(jepInterpreterProvider, properties.getResultAppearance());
    }

    @Bean
    @ConditionalOnMissingBean(JepFinalizer.class)
    @ConditionalOnBooleanProperty(name = "spring.python.executor.jep.library.enabled", matchIfMissing = true)
    public PythonFinalizer jepFinalizer(PipManager pipManager, JepProperties properties) {
        return new JepFinalizer(pipManager, properties.getLibrary().getUninstall());
    }
}