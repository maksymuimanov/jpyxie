package io.jpyxie.python.autoconfigure;

import io.conditionals.condition.ConditionalOnOs;
import io.jpyxie.python.environment.AbstractVenvPythonEnvironment;
import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.environment.UnixVenvPythonEnvironment;
import io.jpyxie.python.environment.WindowsVenvPythonEnvironment;
import io.jpyxie.python.lifecycle.PythonEnvironmentFinalizer;
import io.jpyxie.python.lifecycle.PythonEnvironmentInitializer;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInitializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnBooleanProperty(name = "spring.python.environment.enabled", matchIfMissing = true)
@EnableConfigurationProperties(PythonEnvironmentProperties.class)
public class PythonEnvironmentAutoConfiguration {
    @Bean
    @ConditionalOnOs({"linux", "mac"})
    @ConditionalOnMissingBean(PythonEnvironment.class)
    public PythonEnvironment unixVenvPythonEnvironment(PythonEnvironment.ExistingEnvironmentHandler existingEnvironmentHandler,
                                                       PythonEnvironmentProperties properties) {
        return new UnixVenvPythonEnvironment(
                properties.getGlobalPythonExecutable(),
                properties.getBackupPythonExecutable(),
                existingEnvironmentHandler,
                properties.getParentDirectory(),
                properties.isRedirectErrorStream(),
                properties.isRedirectOutputStream(),
                properties.isReadOutput(),
                properties.getTimeout()
        );
    }

    @Bean
    @ConditionalOnOs("windows")
    @ConditionalOnMissingBean(PythonEnvironment.class)
    public PythonEnvironment windowsVenvPythonEnvironment(PythonEnvironment.ExistingEnvironmentHandler existingEnvironmentHandler,
                                                          PythonEnvironmentProperties properties) {
        return new WindowsVenvPythonEnvironment(
                properties.getGlobalPythonExecutable(),
                properties.getBackupPythonExecutable(),
                existingEnvironmentHandler,
                properties.getParentDirectory(),
                properties.isRedirectErrorStream(),
                properties.isRedirectOutputStream(),
                properties.isReadOutput(),
                properties.getTimeout()
        );
    }

    @Bean
    @ConditionalOnMissingBean(PythonEnvironment.ExistingEnvironmentHandler.class)
    public PythonEnvironment.ExistingEnvironmentHandler existingHandler() {
        return new AbstractVenvPythonEnvironment.RemoveExistingEnvironmentHandler();
    }

    @Bean
    @ConditionalOnBooleanProperty(name = "spring.python.environment.create-on-start")
    @ConditionalOnMissingBean(PythonEnvironmentInitializer.class)
    public PythonInitializer pythonEnvironmentInitializer(PythonEnvironment pythonEnvironment) {
        return new PythonEnvironmentInitializer(pythonEnvironment);
    }

    @Bean
    @ConditionalOnBooleanProperty(name = "spring.python.environment.remove-on-close")
    @ConditionalOnMissingBean(PythonEnvironmentFinalizer.class)
    public PythonFinalizer pythonEnvironmentFinalizer(PythonEnvironment pythonEnvironment) {
        return new PythonEnvironmentFinalizer(pythonEnvironment);
    }
}
