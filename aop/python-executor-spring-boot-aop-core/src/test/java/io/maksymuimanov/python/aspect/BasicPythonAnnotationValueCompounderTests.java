package io.maksymuimanov.python.aspect;

import io.maksymuimanov.python.annotation.PythonBefore;
import io.maksymuimanov.python.exception.AnnotationExtractionException;
import io.maksymuimanov.python.util.TestUtils;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static io.maksymuimanov.python.constant.TestConstants.SIMPLE_SCRIPT_0;
import static io.maksymuimanov.python.constant.TestConstants.TEST_PROFILES;

@ExtendWith(MockitoExtension.class)
class BasicPythonAnnotationValueCompounderTests {
    @InjectMocks
    private BasicPythonAnnotationValueCompounder basicPythonAnnotationValueExtractorChain;
    @Mock
    private PythonAnnotationValueExtractor annotationValueExtractor;
    @Mock
    private JoinPoint joinPoint;

    @BeforeEach
    void init() {
        TestUtils.setField(basicPythonAnnotationValueExtractorChain, "annotationValueExtractors", List.of(annotationValueExtractor));
    }

    @Test
    void testCompound() {
        Map<String, String[]> annotationValue = Map.of(SIMPLE_SCRIPT_0, TEST_PROFILES);

        Mockito.when(annotationValueExtractor.getValue(joinPoint, PythonBefore.class)).thenReturn(annotationValue);

        Assertions.assertEquals(annotationValue, basicPythonAnnotationValueExtractorChain.compound(joinPoint, PythonBefore.class));
    }

    @Test
    void testCompoundWithException() {
        Assertions.assertThrows(AnnotationExtractionException.class, () -> basicPythonAnnotationValueExtractorChain.compound(joinPoint, PythonBefore.class));
    }
}
