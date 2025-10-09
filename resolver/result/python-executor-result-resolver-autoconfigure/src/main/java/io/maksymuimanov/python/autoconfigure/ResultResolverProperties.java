package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.resolver.ResultResolver;
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
 *         printed: true
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
    /**
     * Regular expression pattern used to locate result expressions in Python scripts that should be captured and serialized.
     */
    private String regex = "o4java\\{.+?}";
    /**
     * The name of the variable that will hold the JSON-serialized result within the Python script.
     */
    private String appearance = "r4java";
    /**
     * Number of characters to skip from the start of a matched expression before extracting the actual content.
     */
    private int positionFromStart = 7;
    /**
     * Number of characters to skip from the end of a matched expression when extracting its body content.
     */
    private int positionFromEnd = 1;
    /**
     * Determines whether the resolver should automatically print the serialized result back to the output stream.
     */
    private boolean printed = true;
}
