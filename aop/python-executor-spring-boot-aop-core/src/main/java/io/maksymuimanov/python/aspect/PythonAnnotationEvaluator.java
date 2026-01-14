package io.maksymuimanov.python.aspect;

import org.aspectj.lang.JoinPoint;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Interface defining the contract for evaluating Python-related annotations on methods.
 * <p>
 * Implementations perform resolution and execution of Python scripts specified
 * by annotations present on the intercepted method represented by the {@link JoinPoint}.
 * </p>
 * <p>
 * Evaluation may involve extracting script content, resolving arguments,
 * checking active profiles, and executing the scripts.
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * PythonAnnotationEvaluator evaluator = ...;
 * evaluator.evaluate(joinPoint, PythonAfter.class);
 * }</pre>
 *
 * @see BasicPythonAnnotationEvaluator
 * @see AsyncPythonAnnotationEvaluator
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface PythonAnnotationEvaluator {
    default void evaluate(JoinPoint joinPoint, Annotation annotation) {
        this.evaluate(joinPoint, annotation, Map.of());
    }

    void evaluate(JoinPoint joinPoint, Annotation annotation, Map<String, Object> additionalArguments);
}
