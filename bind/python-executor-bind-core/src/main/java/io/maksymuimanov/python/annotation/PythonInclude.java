package io.maksymuimanov.python.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PythonInclude {
    AccessModifier visibleFields() default AccessModifier.PRIVATE;
    boolean staticFields() default true;

    enum AccessModifier {
        PUBLIC,
        PROTECTED,
        PRIVATE
    }
}
