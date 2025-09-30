package io.w4t3rcs.python.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.w4t3rcs.python.properties.SpelythonResolverProperties;
import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import io.w4t3rcs.python.resolver.SpelythonResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * Main configuration class for {@link SpelythonResolver}.
 * <p>
 * This configuration class defines and registers {@link SpelythonResolver} as Spring bean
 * </p>
 * <p>
 * Each {@link PythonResolver} bean is assigned an order that determines the sequence of
 * resolver application when used collectively.
 * </p>
 * <p>
 *
 * @see PythonResolver
 * @see SpelythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(SpelythonResolverProperties.class)
@PropertySource("classpath:python-spelython-default.properties")
public class SpelythonResolverAutoConfiguration {
    /**
     * Order value for {@link SpelythonResolver} bean.
     */
    public static final int SPELYTHON_RESOLVER_ORDER = 0;

    /**
     * Creates a {@link SpelythonResolver} bean.
     *
     * @param resolverProperties {@link SpelythonResolverProperties} bean, must not be null
     * @param applicationContext the Spring {@link ApplicationContext}, must not be null
     * @param objectMapper Jackson {@link ObjectMapper} bean, must not be null
     * @return configured {@link SpelythonResolver} instance, never null
     */
    @Bean
    @Order(SPELYTHON_RESOLVER_ORDER)
    public PythonResolver spelythonResolver(SpelythonResolverProperties resolverProperties, ApplicationContext applicationContext, ObjectMapper objectMapper) {
        return new SpelythonResolver(resolverProperties, applicationContext, objectMapper);
    }
}