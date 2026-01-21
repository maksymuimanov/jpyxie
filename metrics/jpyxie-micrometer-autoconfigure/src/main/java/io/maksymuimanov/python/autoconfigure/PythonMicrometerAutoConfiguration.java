package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.metrics.MeteredPythonProcessor;
import io.maksymuimanov.python.processor.PythonProcessor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@AutoConfiguration
@ConditionalOnClass(PythonProcessor.class)
@ConditionalOnBean(PythonProcessor.class)
@EnableConfigurationProperties(PythonMicrometerProperties.class)
public class PythonMicrometerAutoConfiguration {
    @Bean
    @Primary
    public PythonProcessor meteredPythonProcessor(PythonProcessor pythonProcessor, MeterRegistry meterRegistry, PythonMicrometerProperties micrometerProperties) {
        return new MeteredPythonProcessor(pythonProcessor, meterRegistry, micrometerProperties.getPercentiles());
    }
}
