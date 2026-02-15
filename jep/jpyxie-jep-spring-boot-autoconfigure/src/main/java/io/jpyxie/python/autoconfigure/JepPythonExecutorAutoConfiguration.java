package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.bind.JepPythonDeserializer;
import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.executor.JepPythonExecutor;
import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.interpreter.JepInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.library.PipManager;
import io.jpyxie.python.lifecycle.JepFinalizer;
import io.jpyxie.python.lifecycle.JepInitializer;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInitializer;
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
    @ConditionalOnMissingBean(PythonDeserializer.class)
    public PythonDeserializer<Interpreter> jepPythonDeserializer() {
        return new JepPythonDeserializer();
    }

    @Bean
    @ConditionalOnMissingBean(PythonInterpreterFactory.class)
    public PythonInterpreterFactory<Interpreter> jepInterpreterFactory(JepProperties properties) {
        return new JepInterpreterFactory(properties.getInterpreterType());
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor jepPythonExecutor(PythonDeserializer<Interpreter> jepPythonDeserializer, PythonInterpreterProvider<Interpreter> jepInterpreterProvider) {
        return new JepPythonExecutor(jepPythonDeserializer, jepInterpreterProvider);
    }

    @Bean
    @ConditionalOnMissingBean(JepFinalizer.class)
    @ConditionalOnBooleanProperty(name = "spring.python.executor.jep.library.enabled", matchIfMissing = true)
    public PythonFinalizer jepFinalizer(PipManager pipManager, JepProperties properties) {
        return new JepFinalizer(pipManager, properties.getLibrary().getUninstall());
    }
}