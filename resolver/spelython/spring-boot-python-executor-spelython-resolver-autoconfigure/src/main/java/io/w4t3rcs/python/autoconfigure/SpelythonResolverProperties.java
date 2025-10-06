package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import io.w4t3rcs.python.resolver.SpelythonResolver;
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
    private String regex = "spel\\{.+?}";
    private String localVariableIndex = "#";
    private int positionFromStart = 5;
    private int positionFromEnd = 1;
}
