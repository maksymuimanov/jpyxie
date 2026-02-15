package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.bind.GraalPythonDeserializer;
import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.executor.GraalPythonExecutor;
import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.interpreter.GraalInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
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
    @ConditionalOnMissingBean(PythonDeserializer.class)
    public PythonDeserializer<Value> graalPythonDeserializer() {
        return new GraalPythonDeserializer();
    }

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
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor graalPythonExecutor(PythonInterpreterProvider<Context> graalInterpreterProvider,
                                              PythonDeserializer<Value> graalPythonDeserializer,
                                              GraalPyProperties properties) {
        return new GraalPythonExecutor(graalPythonDeserializer, graalInterpreterProvider, properties.isCached());
    }
}