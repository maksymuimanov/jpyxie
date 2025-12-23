package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.PythonResultFieldNameProvider;
import io.maksymuimanov.python.executor.UuidPythonResultFieldNameProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(PythonExecutorProperties.class)
public class PythonExecutorAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(PythonResultFieldNameProvider.class)
    public PythonResultFieldNameProvider resultFieldNameProvider(PythonExecutorProperties executorProperties) {
        return new UuidPythonResultFieldNameProvider(executorProperties.getResultAppearance());
    }
}
