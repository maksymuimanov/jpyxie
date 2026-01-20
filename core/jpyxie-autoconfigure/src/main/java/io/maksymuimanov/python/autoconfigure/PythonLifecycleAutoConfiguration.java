package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.lifecycle.PythonFinalizer;
import io.maksymuimanov.python.lifecycle.PythonInitializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;

@AutoConfiguration
public class PythonLifecycleAutoConfiguration {
    @EventListener(classes = ApplicationStartedEvent.class)
    public void initialize(ApplicationStartedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, PythonInitializer> pythonInitializers = applicationContext.getBeansOfType(PythonInitializer.class);
        pythonInitializers.forEach((beanName, initializer) -> initializer.initialize());
    }

    @EventListener(classes = ContextClosedEvent.class)
    public void finish(ContextClosedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, PythonFinalizer> pythonFinalizers = applicationContext.getBeansOfType(PythonFinalizer.class);
        pythonFinalizers.forEach((beanName, finalizer) -> finalizer.finish());
    }
}