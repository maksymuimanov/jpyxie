package io.maksymuimanov.python.aspect;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.task.TaskExecutor;

import java.lang.annotation.Annotation;

@ExtendWith(MockitoExtension.class)
class AsyncPythonAnnotationEvaluatorTests {
    @InjectMocks
    private AsyncPythonAnnotationEvaluator asyncPythonAnnotationEvaluator;
    @Mock
    private PythonAnnotationEvaluator pythonAnnotationEvaluator;
    @Mock
    private TaskExecutor taskExecutor;
    @Mock
    private JoinPoint joinPoint;

    @Test
    void testEvaluate() {
        Assertions.assertDoesNotThrow(() -> asyncPythonAnnotationEvaluator.evaluate(joinPoint, Annotation.class));
    }
}
