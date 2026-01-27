package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.exception.PythonFileException;
import io.maksymuimanov.python.file.BasicPythonFileReader;
import io.maksymuimanov.python.file.InputStreamProvider;
import io.maksymuimanov.python.file.PythonFileReader;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

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
public class PythonFileAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(InputStreamProvider.class)
    public InputStreamProvider classPathResourceInputStreamProvider(PythonFileProperties fileProperties, Environment environment) {
        return path -> {
            try {
                ClassPathResource resource = new ClassPathResource(fileProperties.getPath() + path);
                if (resource.exists()) {
                    return resource.getInputStream();
                } else {
                    String[] activeProfiles = environment.getActiveProfiles();
                    for (String activeProfile : activeProfiles) {
                        ClassPathResource profileResource = new ClassPathResource(fileProperties.getPath() + activeProfile + "/" + path);
                        if (profileResource.exists()) {
                            return profileResource.getInputStream();
                        }
                    }
                    throw new PythonFileException(path + " not found");
                }
            } catch (IOException e) {
                throw new PythonFileException(e);
            }
        };
    }

    /**
     * Creates a {@link BasicPythonFileReader} with configuration from {@link PythonFileProperties}.
     *
     * @param fileProperties non-null configuration properties for file handling, must contain valid directory paths and permissions.
     * @return never {@code null}, an initialized instance of {@link BasicPythonFileReader}.
     */
    @Bean
    @ConditionalOnMissingBean(PythonFileReader.class)
    public PythonFileReader basicPythonFileHandler(PythonFileProperties fileProperties, InputStreamProvider inputStreamProvider) {
        return new BasicPythonFileReader(inputStreamProvider, fileProperties.getCharset());
    }
}
