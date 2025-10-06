package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.file.BasicPythonFileReader;
import io.w4t3rcs.python.file.PythonFileReader;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot autoconfiguration for {@link PythonFileReader} beans.
 *
 * <p>This configuration registers a default {@link BasicPythonFileReader} implementation
 * when no custom {@link PythonFileReader} bean is already defined in the application context.</p>
 *
 * <p>It reads file-related settings from {@link PythonFileProperties} and uses them to initialize
 * the handler instance.</p>
 *
 * @see PythonFileReader
 * @see BasicPythonFileReader
 * @see PythonFileProperties
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(PythonFileProperties.class)
public class PythonFileReaderAutoConfiguration {
    /**
     * Creates a {@link BasicPythonFileReader} with configuration from {@link PythonFileProperties}.
     *
     * @param fileProperties non-null configuration properties for file handling, must contain valid directory paths and permissions.
     * @return never {@code null}, an initialized instance of {@link BasicPythonFileReader}.
     */
    @Bean
    @ConditionalOnMissingBean(PythonFileReader.class)
    public PythonFileReader basicPythonFileHandler(PythonFileProperties fileProperties) {
        return new BasicPythonFileReader(fileProperties.getPath());
    }
}
