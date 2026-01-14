package io.maksymuimanov.python.aspect;

import io.maksymuimanov.python.annotation.*;
import io.maksymuimanov.python.exception.PythonAspectException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PythonAspectUtils {
    private PythonAspectUtils() {
    }

    public static PythonAnnotatedMethodDescriptor createAnnotatedMethodDescriptor(JoinPoint joinPoint, Annotation annotation) {
        PythonMethodDescriptor methodDescriptor = createMethodDescriptor(joinPoint);
        List<PythonAnnotationDescriptor> annotationDescriptors = createMultipleAnnotationDescriptor(annotation);
        return new PythonAnnotatedMethodDescriptor(methodDescriptor, annotationDescriptors);
    }

    public static PythonMethodDescriptor createMethodDescriptor(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
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
            return new PythonMethodDescriptor(method, methodParameters);
        } catch (Exception e) {
            throw new PythonAspectException(e);
        }
    }

    public static List<PythonAnnotationDescriptor> createMultipleAnnotationDescriptor(Annotation annotation) {
        List<PythonAnnotationDescriptor> descriptors = new ArrayList<>();
        if (annotation instanceof PythonBefores pythonBefores) {
            for (PythonBefore pythonBefore : pythonBefores.value()) {
                PythonAnnotationDescriptor descriptor = createAnnotationDescriptor(pythonBefore);
                descriptors.add(descriptor);
            }
        } else if (annotation instanceof PythonBefore pythonBefore) {
            PythonAnnotationDescriptor descriptor = createAnnotationDescriptor(pythonBefore);
            descriptors.add(descriptor);
        } else if (annotation instanceof PythonAfters pythonAfters) {
            for (PythonAfter pythonAfter : pythonAfters.value()) {
                PythonAnnotationDescriptor descriptor = createAnnotationDescriptor(pythonAfter);
                descriptors.add(descriptor);
            }
        } else if (annotation instanceof PythonAfter pythonAfter) {
            PythonAnnotationDescriptor descriptor = createAnnotationDescriptor(pythonAfter);
            descriptors.add(descriptor);
        }
        return descriptors;
    }

    public static PythonAnnotationDescriptor createAnnotationDescriptor(Annotation annotation) {
        if (annotation instanceof PythonBefore pythonBefore) {
            return new PythonAnnotationDescriptor(pythonBefore.name(), pythonBefore.script(), pythonBefore.isFile(), pythonBefore.activeProfiles());
        } else if (annotation instanceof PythonAfter pythonAfter) {
            return new PythonAnnotationDescriptor(pythonAfter.name(), pythonAfter.script(), pythonAfter.isFile(), pythonAfter.activeProfiles());
        } else {
            throw new PythonAspectException();
        }
    }

    public record PythonAnnotatedMethodDescriptor(PythonMethodDescriptor methodDescriptor, List<PythonAnnotationDescriptor> annotationDescriptors) {
    }

    public record PythonMethodDescriptor(Method method, Map<String, Object> arguments) {
        public void addAdditionalArguments(Map<String, Object> additionalArguments) {
            this.arguments.putAll(additionalArguments);
        }
    }

    public record PythonAnnotationDescriptor(String name, String script, boolean isFile, String[] activeProfiles) {
    }
}
