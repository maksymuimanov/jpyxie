package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.bind.GraalPythonDeserializer;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.executor.GraalPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.interpreter.GraalInterpreterFactory;
import io.maksymuimanov.python.interpreter.PythonInterpreterFactory;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(GraalPyProperties.class)
@ConditionalOnBooleanProperty(name = "spring.python.executor.graalpy.enabled", matchIfMissing = true)
public class GraalPythonExecutorAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(PythonInterpreterFactory.class)
    public PythonInterpreterFactory<Context> graalInterpreterFactory(GraalPyProperties properties) {
        return new GraalInterpreterFactory(
                properties.getHostAccess().getValue(),
                properties.isAllowValueSharing(),
                properties.isAllowExperimentalOptions(),
                properties.getAdditionalOptions()
        );
    }

    @Bean
    @ConditionalOnMissingBean(PythonDeserializer.class)
    public PythonDeserializer<Value> graalPythonDeserializer() {
        return new GraalPythonDeserializer();
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor graalPythonExecutor(PythonInterpreterProvider<Context> graalInterpreterProvider, PythonDeserializer<Value> graalPythonDeserializer, GraalPyProperties properties) {
        return new GraalPythonExecutor(graalInterpreterProvider, graalPythonDeserializer, properties.isCached());
    }
}