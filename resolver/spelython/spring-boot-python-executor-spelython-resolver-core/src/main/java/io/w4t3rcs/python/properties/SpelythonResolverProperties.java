package io.w4t3rcs.python.properties;

import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import io.w4t3rcs.python.resolver.SpelythonResolver;
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
 * @param regex the regular expression pattern to match SpEL expressions, non-null
 * @param localVariableIndex the name of the local variable index, non-null
 * @param positionFromStart the amount characters to skip from start, >= 0
 * @param positionFromEnd the amount characters to skip from end, >= 0
 * @see SpelythonResolver
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@ConfigurationProperties("spring.python.resolver.spelython")
public record SpelythonResolverProperties(String regex,
                                          String localVariableIndex,
                                          int positionFromStart,
                                          int positionFromEnd) {
}
