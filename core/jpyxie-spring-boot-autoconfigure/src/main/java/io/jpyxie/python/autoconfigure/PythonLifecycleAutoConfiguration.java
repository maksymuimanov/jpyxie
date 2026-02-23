package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.exception.PythonLifecycleException;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInitializer;
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
        try {
            ApplicationContext applicationContext = event.getApplicationContext();
            Map<String, PythonInitializer> pythonInitializers = applicationContext.getBeansOfType(PythonInitializer.class);
            pythonInitializers.forEach((beanName, initializer) -> initializer.initialize());
        } catch (Exception e) {
            throw new PythonLifecycleException(e);
        }
    }

    @EventListener(classes = ContextClosedEvent.class)
    public void finish(ContextClosedEvent event) {
        try {
            ApplicationContext applicationContext = event.getApplicationContext();
            Map<String, PythonFinalizer> pythonFinalizers = applicationContext.getBeansOfType(PythonFinalizer.class);
            pythonFinalizers.forEach((beanName, finalizer) -> finalizer.finish());
        } catch (Exception e) {
            throw new PythonLifecycleException(e);
        }
    }
}