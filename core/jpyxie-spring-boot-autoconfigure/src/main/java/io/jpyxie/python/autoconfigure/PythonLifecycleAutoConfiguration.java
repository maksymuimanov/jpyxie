package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.exception.PythonLifecycleException;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInitializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

@AutoConfiguration
public class PythonLifecycleAutoConfiguration {
    @EventListener(classes = ApplicationStartedEvent.class)
    public void initialize(ApplicationStartedEvent event) {
        try {
            ApplicationContext applicationContext = event.getApplicationContext();
            ObjectProvider<PythonInitializer> beanProvider = applicationContext.getBeanProvider(PythonInitializer.class);
            beanProvider.stream()
                    .sorted()
                    .forEach(PythonInitializer::initialize);
        } catch (Exception e) {
            throw new PythonLifecycleException(e);
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
            throw new PythonLifecycleException(e);
        }
    }
}