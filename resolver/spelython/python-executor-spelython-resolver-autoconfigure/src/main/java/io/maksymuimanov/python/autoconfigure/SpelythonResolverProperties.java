package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.resolver.SpelythonResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties forconfiguring {@link SpelythonResolver}.
 *
 * <p>Properties are bound from the application configuration using the prefix
 * {@code spring.python.resolver}.</p>
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     resolver:
 *       spelython:
 *         regex: spel\\{.+?}
 *         local-variable-index: #
 *         position-from-start: 5
 *         position-from-end: 1
 * }</pre>
 * </p>
 *
 * @author w4t3rcs
 * @see SpelythonResolver
 * @see PythonResolver
 * @see PythonResolverHolder
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.resolver.spelython")
public class SpelythonResolverProperties {
    /**
     * Regular expression pattern used to locate SpEL expressions within Python scripts.
     */
    private String regex = "spel\\{.+?}";
    /**
     * Prefix symbol used to reference additional arguments variables in SpEL expressions.
     */
    private String localVariableIndex = "#";
    /**
     * Number of characters to skip from the start of a matched SpEL expression before evaluating it.
     */
    private int positionFromStart = 5;
    /**
     * Number of characters to skip from the end of a matched SpEL expression before evaluation.
     */
    private int positionFromEnd = 1;
}
