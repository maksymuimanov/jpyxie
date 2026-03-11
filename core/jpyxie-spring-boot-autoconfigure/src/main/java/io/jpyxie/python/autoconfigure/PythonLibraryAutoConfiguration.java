package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.library.PythonLibraryManager;
import io.jpyxie.python.library.SubprocessPythonLibraryManager;
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
@EnableConfigurationProperties(PythonLibraryProperties.class)
@ConditionalOnBooleanProperty("spring.python.library.enabled")
public class PythonLibraryAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(PythonLibraryManager.class)
    public PythonLibraryManager subprocessPythonLibraryManager(PythonExecutor pythonExecutor) {
        return new SubprocessPythonLibraryManager(pythonExecutor);
    }

    @Bean
    @ConditionalOnMissingBean(PythonLibraryInitializer.class)
    public PythonInitializer externalPythonLibraryInitializer(PythonLibraryManager pythonLibraryManager, PythonLibraryProperties libraryProperties) {
        return new PythonLibraryInitializer(pythonLibraryManager, libraryProperties.getInstalled());
    }

    @Bean
    @ConditionalOnMissingBean(PythonLibraryFinalizer.class)
    public PythonFinalizer externalPythonLibraryFinalizer(PythonLibraryManager pythonLibraryManager, PythonLibraryProperties libraryProperties) {
        return new PythonLibraryFinalizer(pythonLibraryManager, libraryProperties.getUninstalled());
    }
}