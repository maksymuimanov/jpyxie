package io.jpyxie.python.autoconfigure;

import io.conditionals.condition.ConditionalOnOs;
import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.environment.UnixVenvPythonEnvironment;
import io.jpyxie.python.environment.VenvPythonEnvironment;
import io.jpyxie.python.environment.WindowsVenvPythonEnvironment;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.library.PipPythonLibraryManager;
import io.jpyxie.python.library.PythonLibraryManager;
import io.jpyxie.python.lifecycle.*;
import io.jpyxie.python.resolver.BasicPythonResolverHolder;
import io.jpyxie.python.resolver.PythonResolver;
import io.jpyxie.python.resolver.PythonResolverHolder;
import io.jpyxie.python.script.SpringPythonScriptFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.util.List;

@AutoConfiguration
@EnableConfigurationProperties({
        PythonEnvironmentProperties.class,
        PythonFileProperties.class,
        PythonLibraryProperties.class
})
public class PythonAutoConfiguration {
    @Bean
    @ConditionalOnBooleanProperty(name = "spring.python.environment.enabled", matchIfMissing = true)
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
    @ConditionalOnBooleanProperty(name = "spring.python.environment.enabled", matchIfMissing = true)
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
    @ConditionalOnBooleanProperty(name = "spring.python.environment.enabled", matchIfMissing = true)
    @ConditionalOnMissingBean(PythonEnvironment.ExistingEnvironmentHandler.class)
    public PythonEnvironment.ExistingEnvironmentHandler existingHandler() {
        return new VenvPythonEnvironment.RemoveExistingEnvironmentHandler();
    }

    @Bean
    @ConditionalOnBooleanProperty(name = "spring.python.environment.enabled", matchIfMissing = true)
    @ConditionalOnBooleanProperty(name = "spring.python.environment.create-on-start")
    @ConditionalOnMissingBean(PythonEnvironmentInitializer.class)
    public PythonInitializer pythonEnvironmentInitializer(PythonEnvironment pythonEnvironment) {
        return new PythonEnvironmentInitializer(pythonEnvironment);
    }

    @Bean
    @ConditionalOnBooleanProperty(name = "spring.python.environment.enabled", matchIfMissing = true)
    @ConditionalOnBooleanProperty(name = "spring.python.environment.remove-on-close")
    @ConditionalOnMissingBean(PythonEnvironmentFinalizer.class)
    public PythonFinalizer pythonEnvironmentFinalizer(PythonEnvironment pythonEnvironment) {
        return new PythonEnvironmentFinalizer(pythonEnvironment);
    }

    @Bean
    @ConditionalOnBooleanProperty("spring.python.library.enabled")
    @ConditionalOnBean(PythonEnvironment.class)
    @ConditionalOnMissingBean(PythonLibraryManager.class)
    public PythonLibraryManager pipPythonLibraryManager(PythonEnvironment pythonEnvironment) {
        return new PipPythonLibraryManager(pythonEnvironment);
    }

    @Bean
    @ConditionalOnBooleanProperty("spring.python.library.enabled")
    @ConditionalOnMissingBean(PythonLibraryInitializer.class)
    public PythonInitializer externalPythonLibraryInitializer(PythonLibraryManager pythonLibraryManager, PythonLibraryProperties libraryProperties) {
        return new PythonLibraryInitializer(pythonLibraryManager, libraryProperties.getInstalled());
    }

    @Bean
    @ConditionalOnBooleanProperty("spring.python.library.enabled")
    @ConditionalOnMissingBean(PythonLibraryFinalizer.class)
    public PythonFinalizer externalPythonLibraryFinalizer(PythonLibraryManager pythonLibraryManager, PythonLibraryProperties libraryProperties) {
        return new PythonLibraryFinalizer(pythonLibraryManager, libraryProperties.getUninstalled());
    }

    @Bean
    @ConditionalOnBean(PythonInterpreterProvider.class)
    @ConditionalOnMissingBean(PythonInterpreterProviderFinalizer.class)
    public PythonFinalizer pythonInterpreterProviderFinalizer(PythonInterpreterProvider<?> interpreterProvider) {
        return new PythonInterpreterProviderFinalizer(interpreterProvider);
    }

    @Bean
    @ConditionalOnMissingBean(PythonResolverHolder.class)
    public PythonResolverHolder basicPythonResolverHolder(List<PythonResolver> pythonResolvers) {
        return new BasicPythonResolverHolder(pythonResolvers);
    }

    @Bean
    @ConditionalOnMissingBean(SpringPythonScriptFactory.class)
    public SpringPythonScriptFactory pythonScriptFactory(PythonFileProperties fileProperties, Environment environment) {
        return new SpringPythonScriptFactory(fileProperties, environment);
    }

    @EventListener(classes = ApplicationStartedEvent.class)
    public void initialize(ApplicationStartedEvent event) {
        try {
            ApplicationContext applicationContext = event.getApplicationContext();
            ObjectProvider<PythonInitializer> beanProvider = applicationContext.getBeanProvider(PythonInitializer.class);
            beanProvider.stream()
                    .sorted()
                    .forEach(PythonInitializer::initialize);
        } catch (Exception e) {
            throw SpringEventPythonLifecycleException.failedToInitialize(e);
        }
    }

    @EventListener(classes = ContextClosedEvent.class)
    public void finish(ContextClosedEvent event) {
        try {
            ApplicationContext applicationContext = event.getApplicationContext();
            ObjectProvider<PythonFinalizer> beanProvider = applicationContext.getBeanProvider(PythonFinalizer.class);
            beanProvider.orderedStream()
                    .forEach(PythonFinalizer::finish);
        } catch (Exception e) {
            throw SpringEventPythonLifecycleException.failedToFinish(e);
        }
    }
}
