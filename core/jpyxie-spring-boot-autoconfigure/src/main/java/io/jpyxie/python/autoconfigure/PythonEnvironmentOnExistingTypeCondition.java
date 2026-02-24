package io.jpyxie.python.autoconfigure;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class PythonEnvironmentOnExistingTypeCondition implements Condition {
    private static final String SPRING_PYTHON_ENVIRONMENT_ON_EXISTING_PROPERTY_KEY = "spring.python.environment.on-existing";
    private static final String DEFAULT_TYPE = PythonEnvironmentProperties.OnExisting.SKIP.name();
    private final PythonEnvironmentProperties.OnExisting onExisting;

    protected PythonEnvironmentOnExistingTypeCondition(PythonEnvironmentProperties.OnExisting onExisting) {
        this.onExisting = onExisting;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String property = environment.getProperty(SPRING_PYTHON_ENVIRONMENT_ON_EXISTING_PROPERTY_KEY, String.class, DEFAULT_TYPE);
        String typeName = property.toUpperCase();
        return this.onExisting.name().equals(typeName);
    }
}
