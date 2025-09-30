package io.w4t3rcs.python.properties;

import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import io.w4t3rcs.python.resolver.ResultResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for configuring {@link ResultResolver}.
 *
 * <p>Properties are bound from the application configuration using the prefix
 * {@code spring.python.resolver.result}.</p>
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     resolver:
 *       result:
 *         regex: o4java\\{.+?}
 *         appearance: r4java
 *         position-from-start: 7
 *         position-from-end: 1
 *         is-printed: true
 * }</pre>
 * </p>
 *
 * @param regex regular expression to match body lines, non-null
 * @param appearance prefix string indicating body presence, non-null
 * @param positionFromStart number of characters to skip from start, >= 0
 * @param positionFromEnd number of characters to skip from end, >= 0
 * @see ResultResolver
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@ConfigurationProperties("spring.python.resolver.result")
public record ResultResolverProperties(String regex, String appearance, int positionFromStart, int positionFromEnd, boolean isPrinted) {
}
