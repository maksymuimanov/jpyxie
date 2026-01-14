package io.maksymuimanov.python.aspect;

import io.maksymuimanov.python.annotation.PythonBefore;
import io.maksymuimanov.python.annotation.PythonBefores;
import io.maksymuimanov.python.exception.PythonAspectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.annotation.Annotation;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class PythonBeforeAspect {
    private static final String PYTHON_BEFORE_ASPECT_EXCEPTION_MESSAGE = "Something went wrong in PythonBeforeAspect";
    private final PythonAnnotationEvaluator annotationEvaluator;

    @Before("@annotation(annotation)")
    public void executeMultipleBeforeMethod(JoinPoint joinPoint, PythonBefores annotation) {
        this.evaluateAnnotation(joinPoint, annotation);
    }

    @Before("@annotation(annotation)")
    public void executeSingleBeforeMethod(JoinPoint joinPoint, PythonBefore annotation) {
        this.evaluateAnnotation(joinPoint, annotation);
    }

    private void evaluateAnnotation(JoinPoint joinPoint, Annotation annotation) {
        try {
            this.annotationEvaluator.evaluate(joinPoint, annotation);
        } catch (Exception e) {
            log.error(PYTHON_BEFORE_ASPECT_EXCEPTION_MESSAGE, e);
            throw new PythonAspectException(PYTHON_BEFORE_ASPECT_EXCEPTION_MESSAGE, e);
        }
    }
}