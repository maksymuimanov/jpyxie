package io.w4t3rcs.python.config;

import io.w4t3rcs.python.resolver.PrettyResolver;
import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * Main configuration class for {@link PrettyResolver}.
 * <p>
 * This configuration class defines and registers {@link PrettyResolver} as Spring bean
 * </p>
 * <p>
 * Each {@link PythonResolver} bean is assigned an order that determines the sequence of
 * resolver application when used collectively.
 * </p>
 * <p>
 *
 * @see PythonResolver
 * @see PrettyResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@Configuration
@PropertySource("classpath:python-result-default.properties")
public class PrettyResolverAutoConfiguration {
    /**
     * Order value for {@link PrettyResolver} bean.
     */
    public static final int RESULT_RESOLVER_ORDER = 200;

    /**
     * Creates a {@link PrettyResolver} bean.
     *
     * @return configured {@link PrettyResolver} instance, never null
     */
    @Bean
    @Order(RESULT_RESOLVER_ORDER)
    public PythonResolver resultResolver() {
        return new PrettyResolver();
    }
}