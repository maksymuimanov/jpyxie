package io.maksymuimanov.python.aspect;

import io.maksymuimanov.python.exception.PythonAspectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.core.task.TaskExecutor;

import java.lang.annotation.Annotation;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class AsyncPythonAnnotationEvaluator implements PythonAnnotationEvaluator {
    private static final String ANNOTATION_EVALUATION_EXCEPTION_MESSAGE = "Exception occurred during async Python annotation evaluation";
    private final PythonAnnotationEvaluator delegate;
    private final TaskExecutor taskExecutor;

    @Override
    public void evaluate(JoinPoint joinPoint, Annotation annotation, Map<String, Object> additionalArguments) {
        this.taskExecutor.execute(() -> {
            try {
                this.delegate.evaluate(joinPoint, annotation, additionalArguments);
            } catch (Exception e) {
                log.error(ANNOTATION_EVALUATION_EXCEPTION_MESSAGE, e);
                throw new PythonAspectException(ANNOTATION_EVALUATION_EXCEPTION_MESSAGE, e);
            }
        });
    }
}
