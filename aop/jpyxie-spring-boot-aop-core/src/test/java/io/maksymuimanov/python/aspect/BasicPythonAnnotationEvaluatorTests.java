package io.maksymuimanov.python.aspect;

import io.maksymuimanov.python.processor.PythonProcessor;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Annotation;
import java.util.Map;

import static io.maksymuimanov.python.constant.TestConstants.SIMPLE_SCRIPT_0;
import static io.maksymuimanov.python.constant.TestConstants.TEST_PROFILES;

@ExtendWith(MockitoExtension.class)
class BasicPythonAnnotationEvaluatorTests {
    @InjectMocks
    private BasicPythonAnnotationEvaluator basicPythonAnnotationEvaluator;
    @Mock
    private ProfileChecker profileChecker;
    @Mock
    private PythonAnnotationValueCompounder annotationValueExtractorChain;
    @Mock
    private PythonArgumentsExtractor argumentsExtractor;
    @Mock
    private PythonProcessor pythonProcessor;
    @Mock
    private JoinPoint joinPoint;

    @Test
    void testEvaluate() {
        Map<String, String[]> annotationValue = Map.of(SIMPLE_SCRIPT_0, TEST_PROFILES);

        Mockito.when(annotationValueExtractorChain.compound(joinPoint, Annotation.class)).thenReturn(annotationValue);

        Assertions.assertDoesNotThrow(() -> basicPythonAnnotationEvaluator.evaluate(joinPoint, Annotation.class));
    }
}
