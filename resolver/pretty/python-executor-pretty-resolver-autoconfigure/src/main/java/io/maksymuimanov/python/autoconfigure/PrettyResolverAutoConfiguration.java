package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.resolver.PrettyResolver;
import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

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
@AutoConfiguration
public class PrettyResolverAutoConfiguration {
    /**
     * Creates a {@link PrettyResolver} bean.
     *
     * @return configured {@link PrettyResolver} instance, never null
     */
    @Bean
    public PythonResolver prettyResolver() {
        return new PrettyResolver();
    }
}