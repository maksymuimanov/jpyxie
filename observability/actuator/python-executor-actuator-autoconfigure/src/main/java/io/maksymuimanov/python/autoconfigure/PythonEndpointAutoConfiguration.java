package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.actuator.endpoint.PythonEndpoint;
import io.maksymuimanov.python.processor.PythonProcessor;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = PythonProcessorAutoConfiguration.class)
@ConditionalOnClass(PythonProcessor.class)
@ConditionalOnBean(PythonProcessor.class)
@ConditionalOnAvailableEndpoint(PythonEndpoint.class)
public class PythonEndpointAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public PythonEndpoint pythonEndpoint(ApplicationContext context) {
        return new PythonEndpoint(context);
    }
}
