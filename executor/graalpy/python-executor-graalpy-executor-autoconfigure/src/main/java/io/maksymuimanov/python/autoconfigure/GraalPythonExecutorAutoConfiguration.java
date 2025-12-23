package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.GraalPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultFieldNameProvider;
import io.maksymuimanov.python.interpreter.GraalInterpreterFactory;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import org.graalvm.polyglot.Context;
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
    @ConditionalOnMissingBean(GraalInterpreterFactory.class)
    public GraalInterpreterFactory graalInterpreterFactory(GraalPyProperties properties) {
        return new GraalInterpreterFactory(
                properties.getHostAccess().getValue(),
                properties.isAllowValueSharing(),
                properties.isAllowExperimentalOptions(),
                properties.getAdditionalOptions()
        );
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor graalPythonExecutor(PythonResultFieldNameProvider resultFieldNameProvider, PythonInterpreterProvider<Context> graalInterpreterProvider, GraalPyProperties properties) {
        return new GraalPythonExecutor(resultFieldNameProvider, graalInterpreterProvider, properties.isCached());
    }
}