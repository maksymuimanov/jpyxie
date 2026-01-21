package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.actuator.health.PythonHealthIndicator;
import io.maksymuimanov.python.processor.PythonProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = PythonProcessorAutoConfiguration.class)
@ConditionalOnClass(PythonProcessor.class)
@ConditionalOnBean(PythonProcessor.class)
@ConditionalOnEnabledHealthIndicator("python")
public class PythonHealthAutoConfiguration extends CompositeHealthContributorConfiguration<PythonHealthIndicator, PythonProcessor> {
    public PythonHealthAutoConfiguration() {
        super(PythonHealthIndicator::new);
    }

    @Bean
    @ConditionalOnMissingBean(name = {"pythonHealthIndicator", "pythonHealthContributor"})
    public HealthContributor pythonHealthContributor(ConfigurableListableBeanFactory beanFactory) {
        return this.createContributor(beanFactory, PythonProcessor.class);
    }
}
