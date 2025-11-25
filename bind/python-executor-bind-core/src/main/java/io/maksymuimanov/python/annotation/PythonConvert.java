package io.maksymuimanov.python.annotation;

import io.maksymuimanov.python.converter.PythonTypeConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PythonConvert {
    Class<? extends PythonTypeConverter> value();
}
