package io.jpyxie.python.autoconfigure;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.Map;

public class OnOsCondition implements Condition {
    public static final String OS_NAME = System.getProperty("os.name")
            .toLowerCase();
    private static final String VALUE_KEY = "value";

    @Override
    public boolean matches(ConditionContext context,
                           AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnOs.class.getName());
        if (attributes == null)
            return false;
        String[] required = ((String[]) attributes.get(VALUE_KEY));
        return Arrays.stream(required)
                .map(String::toLowerCase)
                .anyMatch(OS_NAME::contains);
    }
}
