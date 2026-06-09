package io.jpyxie.python.annotation;

import java.lang.annotation.*;

@Documented
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
