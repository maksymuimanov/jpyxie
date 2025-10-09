package io.maksymuimanov.python.aspect;

import io.maksymuimanov.python.annotation.PythonParam;
import io.maksymuimanov.python.exception.MethodExtractionException;
import io.maksymuimanov.python.exception.MethodParameterExtractionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic implementation of {@link PythonMethodExtractor} that extracts method metadata and parameters
 * from a given {@link JoinPoint}.
 * <p>
 * This implementation uses the {@link MethodSignature} provided by the join point to retrieve
 * the invoked method and its parameter names and annotations.
 * </p>
 * <p>
 * The {@link #getMethodParameters(JoinPoint)} method extracts parameters by checking if each parameter is annotated with {@link PythonParam}.
 * If so, it uses the annotation's value as the parameter name, otherwise,
 * it falls back to the original parameter name from the signature.
 * </p>
 *
 * @see PythonMethodExtractor
 * @author w4t3rcs
 * @since 1.0.0
 */
public class BasicPythonMethodExtractor implements PythonMethodExtractor {
    /**
     * Retrieves the method from the join point by casting its signature to {@link MethodSignature}.
     *
     * @param joinPoint the join point representing the method invocation; must not be {@code null}
     * @return the invoked {@link Method}; never {@code null} if joinPoint is valid
     */
    @Override
    public Method getMethod(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            return signature.getMethod();
        } catch (Exception e) {
            throw new MethodExtractionException(e);
        }
    }

    /**
     * Extracts the method parameters from the join point, using {@link PythonParam} annotation values
     * as keys if present, otherwise, uses the method parameter names.
     *
     * @param joinPoint the join point representing the method invocation, must not be {@code null}
     * @return a {@link Map} mapping parameter names to argument values, never {@code null}
     */
    @Override
    public Map<String, Object> getMethodParameters(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = this.getMethod(joinPoint);
            Object[] objects = joinPoint.getArgs();
            Parameter[] parameters = method.getParameters();
            String[] parameterNames = signature.getParameterNames();
            Map<String, Object> methodParameters = new HashMap<>();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(PythonParam.class)) {
                    PythonParam annotation = parameter.getAnnotation(PythonParam.class);
                    String parameterName = annotation.value();
                    methodParameters.put(parameterName, objects[i]);
                } else {
                    String parameterName = parameterNames[i];
                    methodParameters.put(parameterName, objects[i]);
                }
            }
            return methodParameters;
        } catch (Exception e) {
            throw new MethodParameterExtractionException(e);
        }
    }
}