package io.maksymuimanov.python.aspect;

import io.maksymuimanov.python.exception.PythonAspectException;
import io.maksymuimanov.python.processor.PythonProcessor;
import io.maksymuimanov.python.resolver.PythonArgumentSpec;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

import java.lang.annotation.Annotation;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class BasicPythonAnnotationEvaluator implements PythonAnnotationEvaluator {
    private static final String ANNOTATION_EVALUATION_EXCEPTION_MESSAGE = "Exception occurred during basic Python annotation evaluation";
    private final ProfileChecker profileChecker;
    private final PythonProcessor pythonProcessor;

    @Override
    public void evaluate(JoinPoint joinPoint, Annotation annotation, Map<String, Object> additionalArguments) {
        try {
            var annotatedMethodDescriptor = PythonAspectUtils.createAnnotatedMethodDescriptor(joinPoint, annotation);
            var methodDescriptor = annotatedMethodDescriptor.methodDescriptor();
            var annotationDescriptors = annotatedMethodDescriptor.annotationDescriptors();
            methodDescriptor.addAdditionalArguments(additionalArguments);
            annotationDescriptors.forEach(annotationDescriptor -> {
                profileChecker.doOnProfiles(annotationDescriptor.activeProfiles(), () -> {
                    PythonScript pythonScript;
                    if (annotationDescriptor.script().isBlank()) {
                        pythonScript = PythonScript.fromFile(annotationDescriptor.name());
                    } else {
                        pythonScript = PythonScript.parse(annotationDescriptor.name(), annotationDescriptor.script());
                    }
                    PythonArgumentSpec pythonArgumentSpec = PythonArgumentSpec.of(methodDescriptor.arguments());
                    pythonProcessor.process(pythonScript, pythonArgumentSpec);
                });
            });
        } catch (Exception e) {
            log.error(ANNOTATION_EVALUATION_EXCEPTION_MESSAGE, e);
            throw new PythonAspectException(ANNOTATION_EVALUATION_EXCEPTION_MESSAGE, e);
        }
    }
}