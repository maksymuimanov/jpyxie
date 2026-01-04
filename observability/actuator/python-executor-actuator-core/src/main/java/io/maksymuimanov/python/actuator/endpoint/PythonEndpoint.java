package io.maksymuimanov.python.actuator.endpoint;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.bind.PythonSerializer;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.file.PythonFileReader;
import io.maksymuimanov.python.interpreter.PythonInterpreterFactory;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.lifecycle.PythonFinalizer;
import io.maksymuimanov.python.lifecycle.PythonInitializer;
import io.maksymuimanov.python.processor.PythonProcessor;
import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.OperationResponseBody;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Endpoint(id = "python")
public class PythonEndpoint {
    private static final Class<?>[] BEAN_TYPES = new Class<?>[]{
            PythonDeserializer.class,
            PythonSerializer.class,
            PythonExecutor.class,
            PythonFileReader.class,
            PythonInterpreterFactory.class,
            PythonInterpreterProvider.class,
            PipManager.class,
            PythonInitializer.class,
            PythonFinalizer.class,
            PythonProcessor.class,
            PythonResolver.class,
            PythonResolverHolder.class
    };
    private final ApplicationContext context;

    public PythonEndpoint(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext must not be null");
        this.context = context;
    }

    @ReadOperation
    public PythonBeansDescriptor pythonBeans() {
        ApplicationContext target = this.context;
        Map<String, ContextPythonBeansDescriptor> contextPythonBeans = new HashMap<>();
        while (target != null) {
            Map<String, PythonBeanDescriptor> pythonBeans = new HashMap<>();
            for (Class<?> beanType : BEAN_TYPES) {
                this.fillBeanNameMap(pythonBeans, target, beanType);
            }
            ApplicationContext parent = target.getParent();
            contextPythonBeans.put(target.getId(), new ContextPythonBeansDescriptor(pythonBeans, parent != null ? parent.getId() : null));
            target = parent;
        }
        return new PythonBeansDescriptor(contextPythonBeans);
    }

    private void fillBeanNameMap(Map<String, PythonBeanDescriptor> beanMap, ApplicationContext context, Class<?> beanClass) {
        try {
            context.getBeansOfType(beanClass)
                    .forEach((name, bean) -> beanMap.put(name, new PythonBeanDescriptor(name, bean, beanClass)));
        } catch (BeansException e) {
            log.debug("No beans of type {}", beanClass.getName(), e);
        }
    }

    public record PythonBeansDescriptor(Map<String, ContextPythonBeansDescriptor> contexts) implements OperationResponseBody {
    }

    public record ContextPythonBeansDescriptor(Map<String, PythonBeanDescriptor> beanNames, String parentId) {
    }

    public record PythonBeanDescriptor(String name, String reference, String type, String parentType) {
        public PythonBeanDescriptor(String name, Object bean, Class<?> parentClass) {
            this(name, bean.getClass().getName(), bean.getClass().getSimpleName(), parentClass.getSimpleName());
        }
    }
}
