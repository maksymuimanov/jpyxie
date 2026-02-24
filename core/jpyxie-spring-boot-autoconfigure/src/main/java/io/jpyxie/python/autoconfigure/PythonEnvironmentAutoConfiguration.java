package io.jpyxie.python.autoconfigure;

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
import org.springframework.context.annotation.Conditional;

@AutoConfiguration
@ConditionalOnBooleanProperty("spring.python.environment.enabled")
@EnableConfigurationProperties(PythonEnvironmentProperties.class)
public class PythonEnvironmentAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(PythonEnvironment.class)
    @ConditionalOnOs({"linux", "mac"})
    public PythonEnvironment unixVenvPythonEnvironment(PythonEnvironment.OnExistingHandler onExistingHandler,
                                                       PythonEnvironmentProperties properties) {
        return new UnixVenvPythonEnvironment(
                properties.getGlobalPythonExecutable(),
                properties.getBackupPythonExecutable(),
                onExistingHandler,
                properties.getParentDirectory(),
                properties.isRedirectErrorStream(),
                properties.isRedirectOutputStream(),
                properties.isReadOutput(),
                properties.getTimeout()
        );
    }

    @Bean
    @ConditionalOnMissingBean(PythonEnvironment.class)
    @ConditionalOnOs("windows")
    public PythonEnvironment windowsVenvPythonEnvironment(PythonEnvironment.OnExistingHandler onExistingHandler,
                                                          PythonEnvironmentProperties properties) {
        return new WindowsVenvPythonEnvironment(
                properties.getGlobalPythonExecutable(),
                properties.getBackupPythonExecutable(),
                onExistingHandler,
                properties.getParentDirectory(),
                properties.isRedirectErrorStream(),
                properties.isRedirectOutputStream(),
                properties.isReadOutput(),
                properties.getTimeout()
        );
    }

    @Bean
    @Conditional(PythonEnvironmentOnExistingSkipCondition.class)
    public PythonEnvironment.OnExistingHandler skipExistingHandler() {
        return new AbstractVenvPythonEnvironment.SkipExistingHandler();
    }

    @Bean
    @Conditional(PythonEnvironmentOnExistingFailCondition.class)
    public PythonEnvironment.OnExistingHandler failExistingHandler() {
        return new AbstractVenvPythonEnvironment.FailExistingHandler();
    }

    @Bean
    @Conditional(PythonEnvironmentOnExistingRemoveCondition.class)
    public PythonEnvironment.OnExistingHandler removeExistingHandler() {
        return new AbstractVenvPythonEnvironment.RemoveExistingHandler();
    }

    @Bean
    @ConditionalOnBooleanProperty("spring.python.environment.create-on-start")
    @ConditionalOnMissingBean(PythonEnvironmentInitializer.class)
    public PythonInitializer pythonEnvironmentInitializer(PythonEnvironment pythonEnvironment) {
        return new PythonEnvironmentInitializer(pythonEnvironment);
    }

    @Bean
    @ConditionalOnBooleanProperty("spring.python.environment.enabled")
    @ConditionalOnMissingBean(PythonEnvironmentFinalizer.class)
    public PythonFinalizer pythonEnvironmentFinalizer(PythonEnvironment pythonEnvironment) {
        return new PythonEnvironmentFinalizer(pythonEnvironment);
    }
}
