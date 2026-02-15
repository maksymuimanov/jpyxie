package io.jpyxie.python.autoconfigure;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class PythonBindTypeCondition implements Condition {
    private static final String SPRING_PYTHON_BIND_TYPE_PROPERTY_KEY = "spring.python.bind.type";
    private static final String DEFAULT_TYPE = PythonBindProperties.Type.JSON.name();
    private final PythonBindProperties.Type type;

    protected PythonBindTypeCondition(PythonBindProperties.Type type) {
        this.type = type;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String property = environment.getProperty(SPRING_PYTHON_BIND_TYPE_PROPERTY_KEY, String.class, DEFAULT_TYPE);
        String typeName = property.toUpperCase();
        return this.type.name().equals(typeName);
    }
}
