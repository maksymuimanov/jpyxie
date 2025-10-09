package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.file.BasicPythonFileReader;
import io.maksymuimanov.python.file.PythonFileReader;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for managing Python files.
 *
 * <p>These properties specify how Python files are located and maintained within the application.</p>
 *
 * <p>Properties are bound from the application configuration using the prefix
 * {@code spring.python.file}.</p>
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     file:
 *       path: /python/
 * }</pre>
 * </p>
 *
 * @see PythonFileReader
 * @see BasicPythonFileReader
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.file")
public class PythonFileProperties {
    /**
     * Base directory path where Python scripts are stored or loaded from.
     */
    private String path = "/python/";
}
