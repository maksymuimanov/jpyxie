package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.resolver.RestrictedPythonResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Main configuration class for {@link RestrictedPythonResolver}.
 * <p>
 * This configuration class defines and registers {@link RestrictedPythonResolver} as Spring bean
 * </p>
 * <p>
 * Each {@link PythonResolver} bean is assigned an order that determines the sequence of
 * resolver application when used collectively.
 * </p>
 * <p>
 *
 * @see PythonResolver
 * @see RestrictedPythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(RestrictedPythonResolverProperties.class)
public class RestrictedPythonResolverAutoConfiguration {
    /**
     * Creates a {@link RestrictedPythonResolver} bean.
     *
     * @param resolverProperties {@link RestrictedPythonResolverProperties} bean, must not be null
     * @return configured {@link RestrictedPythonResolver} instance, never null
     */
    @Bean
    public PythonResolver restrictedPythonResolver(RestrictedPythonResolverProperties resolverProperties) {
        return new RestrictedPythonResolver(resolverProperties.getImportLine(), resolverProperties.getCodeVariableName(), resolverProperties.getLocalVariablesName(), resolverProperties.getResultAppearance(), resolverProperties.isPrinted());
    }
}