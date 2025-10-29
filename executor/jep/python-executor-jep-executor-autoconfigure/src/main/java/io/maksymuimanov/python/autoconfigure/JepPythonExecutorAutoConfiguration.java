package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.JepPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.lifecycle.JepFinalizer;
import io.maksymuimanov.python.lifecycle.JepInitializer;
import io.maksymuimanov.python.lifecycle.PythonFinalizer;
import io.maksymuimanov.python.lifecycle.PythonInitializer;
import jep.Interpreter;
import jep.SharedInterpreter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.util.function.Supplier;

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

    @Lazy
    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean(Interpreter.class)
    public Supplier<Interpreter> jepInterpreterSupplier() {
        return SharedInterpreter::new;
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor jepPythonExecutor(Supplier<Interpreter> jepInterpreterSupplier, JepProperties properties) {
        return new JepPythonExecutor(jepInterpreterSupplier, properties.getResultAppearance());
    }

    @Bean
    @ConditionalOnMissingBean(JepFinalizer.class)
    @ConditionalOnBooleanProperty(name = "spring.python.executor.jep.library.enabled", matchIfMissing = true)
    public PythonFinalizer jepFinalizer(PipManager pipManager, JepProperties properties) {
        return new JepFinalizer(pipManager, properties.getLibrary().getUninstall());
    }
}