package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.resolver.ResultResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
@AutoConfiguration
@EnableConfigurationProperties(ResultResolverProperties.class)
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
        return new ResultResolver(resolverProperties.getRegex(), resolverProperties.getAppearance(), resolverProperties.getPositionFromStart(), resolverProperties.getPositionFromEnd(), resolverProperties.isPrinted());
    }
}