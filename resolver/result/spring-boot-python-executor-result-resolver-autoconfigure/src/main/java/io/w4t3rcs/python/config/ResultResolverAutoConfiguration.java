package io.w4t3rcs.python.config;

import io.w4t3rcs.python.properties.ResultResolverProperties;
import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import io.w4t3rcs.python.resolver.ResultResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * Main configuration class for {@link ResultResolver}.
 * <p>
 * This configuration class defines and registers {@link ResultResolver} as Spring bean
 * </p>
 * <p>
 * Each {@link PythonResolver} bean is assigned an order that determines the sequence of
 * resolver application when used collectively.
 * </p>
 * <p>
 *
 * @see PythonResolver
 * @see ResultResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(ResultResolverProperties.class)
@PropertySource("classpath:python-result-default.properties")
public class ResultResolverAutoConfiguration {
    /**
     * Order value for {@link ResultResolver} bean.
     */
    public static final int RESULT_RESOLVER_ORDER = 100;

    /**
     * Creates a {@link ResultResolver} bean.
     *
     * @param resolverProperties {@link ResultResolverProperties} bean, must not be null
     * @return configured {@link ResultResolver} instance, never null
     */
    @Bean
    @Order(RESULT_RESOLVER_ORDER)
    public PythonResolver resultResolver(ResultResolverProperties resolverProperties) {
        return new ResultResolver(resolverProperties);
    }
}