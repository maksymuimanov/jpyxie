package io.jpyxie.python.annotation;

import io.jpyxie.python.bind.PythonTypeConverter;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PythonConvert {
    Class<? extends PythonTypeConverter> value();
}
