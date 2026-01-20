package io.maksymuimanov.python.aspect;

import io.maksymuimanov.python.annotation.PythonAfter;
import io.maksymuimanov.python.annotation.PythonAfters;
import io.maksymuimanov.python.exception.PythonAspectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.jspecify.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.HashMap;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class PythonAfterAspect {
    private static final String PYTHON_AFTER_ASPECT_EXCEPTION_MESSAGE = "Something went wrong in PythonAfterAspect";
    private final PythonAnnotationEvaluator annotationEvaluator;

    @AfterReturning(pointcut = "@annotation(annotation)", returning = "result")
    public void executeMultipleAfterMethod(JoinPoint joinPoint, PythonAfters annotation, @Nullable Object result) {
        this.evaluateAnnotation(joinPoint, annotation, result);
    }

    @AfterReturning(pointcut = "@annotation(annotation)", returning = "result")
    public void executeSingleAfterMethod(JoinPoint joinPoint, PythonAfter annotation, @Nullable Object result) {
        this.evaluateAnnotation(joinPoint, annotation, result);
    }

    private void evaluateAnnotation(JoinPoint joinPoint, Annotation annotation, @Nullable Object result) {
        try {
            HashMap<String, Object> additionalArguments = new HashMap<>();
            if (result != null) additionalArguments.put("result", result);
            this.annotationEvaluator.evaluate(joinPoint, annotation, additionalArguments);
        } catch (Exception e) {
            log.error(PYTHON_AFTER_ASPECT_EXCEPTION_MESSAGE, e);
            throw new PythonAspectException(PYTHON_AFTER_ASPECT_EXCEPTION_MESSAGE, e);
        }
    }
}