package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.library.BasicPipManager;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.lifecycle.ExternalPythonLibraryFinalizer;
import io.maksymuimanov.python.lifecycle.ExternalPythonLibraryInitializer;
import io.maksymuimanov.python.lifecycle.PythonFinalizer;
import io.maksymuimanov.python.lifecycle.PythonInitializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(PythonPipProperties.class)
public class PipAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(PipManager.class)
    public PipManager basicPipManager(PythonPipProperties pipProperties) {
        return new BasicPipManager(pipProperties.getCommand(), pipProperties.isRedirectErrorStream(), pipProperties.isRedirectOutputStream(), pipProperties.isReadOutput(), pipProperties.getTimeout());
    }

    @Bean
    @ConditionalOnMissingBean(ExternalPythonLibraryInitializer.class)
    @ConditionalOnBooleanProperty("spring.python.pip.library.enabled")
    public PythonInitializer externalPythonLibraryInitializer(PipManager pipManager, PythonPipProperties pipProperties) {
        return new ExternalPythonLibraryInitializer(pipManager, pipProperties.getLibrary().getInstalled());
    }

    @Bean
    @ConditionalOnMissingBean(ExternalPythonLibraryFinalizer.class)
    @ConditionalOnBooleanProperty("spring.python.pip.library.enabled")
    public PythonFinalizer externalPythonLibraryFinalizer(PipManager pipManager, PythonPipProperties pipProperties) {
        return new ExternalPythonLibraryFinalizer(pipManager, pipProperties.getLibrary().getUninstalled());
    }
}