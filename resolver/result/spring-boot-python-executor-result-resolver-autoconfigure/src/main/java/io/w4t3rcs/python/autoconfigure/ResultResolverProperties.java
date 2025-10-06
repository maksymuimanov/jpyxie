package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import io.w4t3rcs.python.resolver.ResultResolver;
import lombok.Getter;
import lombok.Setter;
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
 * @see ResultResolver
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.resolver.result")
public class ResultResolverProperties {
    private String regex = "o4java\\{.+?}";
    private String appearance = "r4java";
    private int positionFromStart = 7;
    private int positionFromEnd = 1;
    private boolean isPrinted = true;
}
