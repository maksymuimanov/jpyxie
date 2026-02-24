package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.library.BasicPipManager;
import io.jpyxie.python.library.PipManager;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInitializer;
import io.jpyxie.python.lifecycle.PythonLibraryFinalizer;
import io.jpyxie.python.lifecycle.PythonLibraryInitializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(PythonPipProperties.class)
public class PythonPipAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(PipManager.class)
    public PipManager basicPipManager(PythonEnvironment pythonEnvironment, PythonPipProperties pipProperties) {
        return new BasicPipManager(pythonEnvironment, pipProperties.getCommand(), pipProperties.isRedirectErrorStream(), pipProperties.isRedirectOutputStream(), pipProperties.isReadOutput(), pipProperties.getTimeout());
    }

    @Bean
    @ConditionalOnMissingBean(PythonLibraryInitializer.class)
    @ConditionalOnBooleanProperty("spring.python.pip.library.enabled")
    public PythonInitializer externalPythonLibraryInitializer(PipManager pipManager, PythonPipProperties pipProperties) {
        return new PythonLibraryInitializer(pipManager, pipProperties.getLibrary().getInstalled());
    }

    @Bean
    @ConditionalOnMissingBean(PythonLibraryFinalizer.class)
    @ConditionalOnBooleanProperty("spring.python.pip.library.enabled")
    public PythonFinalizer externalPythonLibraryFinalizer(PipManager pipManager, PythonPipProperties pipProperties) {
        return new PythonLibraryFinalizer(pipManager, pipProperties.getLibrary().getUninstalled());
    }
}