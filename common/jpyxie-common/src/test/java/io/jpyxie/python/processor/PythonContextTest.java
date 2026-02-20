package io.jpyxie.python.processor;

import io.jpyxie.python.exception.PythonProcessionException;
import io.jpyxie.python.executor.PythonResultSpec;
import io.jpyxie.python.resolver.PythonArgumentSpec;
import io.jpyxie.python.script.PythonScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PythonContextTest {
    private PythonContext context;
    @Mock
    private PythonScript pythonScript;
    private PythonResultSpec resultSpec;
    private PythonArgumentSpec argumentSpec;
    @Mock
    private PythonContext.PreOperator preResolution;
    @Mock
    private PythonContext.PreOperator preExecution;
    @Mock
    private PythonContext.SuccessHandler successHandler;
    @Mock
    private PythonContext.FailureHandler failureHandler;
    private PythonResultMap resultMap;
    @Mock
    private Throwable throwable;

    @BeforeEach
    void setUp() {
        resultSpec = spy(PythonResultSpec.of("result", String.class));
        argumentSpec = spy(PythonArgumentSpec.of("x", 1));
        resultMap = spy(PythonResultMap.of(Map.of("result", "hello world :D")));
        context = PythonContext.builder(pythonScript)
                .resultSpec(resultSpec)
                .argumentSpec(argumentSpec)
                .preResolution(preResolution)
                .preExecution(preExecution)
                .onSuccess(successHandler)
                .onFail(failureHandler)
                .build();
    }

    @Test
    void builder_shouldCreateContextWithDefaultValues_whenOnlyScriptProvided() {
        PythonContext defaultContext = PythonContext.builder(pythonScript).build();

        assertThat(defaultContext.script())
                .isSameAs(pythonScript);
        assertThat(defaultContext.resultSpec())
                .isEqualTo(PythonResultSpec.empty());
        assertThat(defaultContext.argumentSpec())
                .isEqualTo(PythonArgumentSpec.empty());
        assertThat(defaultContext.preResolution())
                .isNotNull();
        assertThat(defaultContext.preExecution())
                .isNotNull();
        assertThat(defaultContext.successHandler())
                .isNotNull();
        assertThat(defaultContext.failureHandler())
                .isNotNull();
    }

    @Test
    void builder_shouldCreateContextWithAllProvidedValues() {
        assertThat(context.script())
                .isSameAs(pythonScript);
        assertThat(context.resultSpec())
                .isSameAs(resultSpec);
        assertThat(context.argumentSpec())
                .isSameAs(argumentSpec);
        assertThat(context.preResolution())
                .isSameAs(preResolution);
        assertThat(context.preExecution())
                .isSameAs(preExecution);
        assertThat(context.successHandler())
                .isSameAs(successHandler);
        assertThat(context.failureHandler())
                .isSameAs(failureHandler);
    }

    @Test
    void builder_shouldSupportFluentApi() {
        PythonContext.Builder builder = PythonContext.builder(pythonScript);
        
        PythonContext builtContext = builder
                .resultSpec(resultSpec)
                .argumentSpec(argumentSpec)
                .preResolution(preResolution)
                .preExecution(preExecution)
                .onSuccess(successHandler)
                .onFail(failureHandler)
                .build();

        assertThat(builtContext.resultSpec())
                .isSameAs(resultSpec);
        assertThat(builtContext.argumentSpec())
                .isSameAs(argumentSpec);
        assertThat(builtContext.preResolution())
                .isSameAs(preResolution);
        assertThat(builtContext.preExecution())
                .isSameAs(preExecution);
        assertThat(builtContext.successHandler())
                .isSameAs(successHandler);
        assertThat(builtContext.failureHandler())
                .isSameAs(failureHandler);
    }

    @Test
    void defaultPreResolution_shouldExecuteWithoutException() {
        PythonContext defaultContext = PythonContext.builder(pythonScript).build();

        assertThatCode(() -> defaultContext.preResolution()
                .operate(pythonScript, resultSpec, argumentSpec))
                .doesNotThrowAnyException();
    }

    @Test
    void defaultPreExecution_shouldExecuteWithoutException() {
        PythonContext defaultContext = PythonContext.builder(pythonScript).build();

        assertThatCode(() -> defaultContext.preExecution()
                .operate(pythonScript, resultSpec, argumentSpec))
                .doesNotThrowAnyException();
    }

    @Test
    void defaultSuccessHandler_shouldReturnSameResultMap() {
        PythonContext defaultContext = PythonContext.builder(pythonScript).build();

        PythonResultMap result = defaultContext.successHandler()
                .onSuccess(resultMap);

        assertThat(result)
                .isSameAs(resultMap);
    }

    @Test
    void defaultFailureHandler_shouldThrowPythonProcessionException() {
        PythonContext defaultContext = PythonContext.builder(pythonScript).build();

        assertThatThrownBy(() -> defaultContext.failureHandler()
                .onFail(throwable))
                .isInstanceOf(PythonProcessionException.class)
                .hasCause(throwable);
    }

    @Test
    void customPreResolution_shouldBeCalledWithCorrectParameters() {
        context.preResolution()
                .operate(pythonScript, resultSpec, argumentSpec);

        verify(preResolution)
                .operate(pythonScript, resultSpec, argumentSpec);
    }

    @Test
    void customPreExecution_shouldBeCalledWithCorrectParameters() {
        context.preExecution()
                .operate(pythonScript, resultSpec, argumentSpec);

        verify(preExecution)
                .operate(pythonScript, resultSpec, argumentSpec);
    }

    @Test
    void customSuccessHandler_shouldBeCalledWithCorrectParameters() {
        when(successHandler.onSuccess(resultMap)).thenReturn(resultMap);

        PythonResultMap result = context.successHandler()
                .onSuccess(resultMap);

        verify(successHandler)
                .onSuccess(resultMap);
        assertThat(result)
                .isSameAs(resultMap);
    }

    @Test
    void customFailureHandler_shouldBeCalledWithCorrectParameters() {
        when(failureHandler.onFail(throwable)).thenReturn(resultMap);

        PythonResultMap result = context.failureHandler()
                .onFail(throwable);

        verify(failureHandler)
                .onFail(throwable);
        assertThat(result)
                .isSameAs(resultMap);
    }

    @Test
    void builder_shouldAllowOverridingDefaultValues() {
        PythonContext.Builder builder = PythonContext.builder(pythonScript);
        
        PythonContext firstBuild = builder
                .resultSpec(resultSpec)
                .build();
        
        PythonContext secondBuild = builder
                .resultSpec(PythonResultSpec.empty())
                .build();

        assertThat(firstBuild.resultSpec())
                .isSameAs(resultSpec);
        assertThat(secondBuild.resultSpec())
                .isNotSameAs(resultSpec)
                .isEqualTo(PythonResultSpec.empty());
    }

    @Test
    void builder_shouldMaintainSeparateState() {
        PythonContext.Builder builder1 = PythonContext.builder(pythonScript);
        PythonContext.Builder builder2 = PythonContext.builder(pythonScript);

        builder1.resultSpec(resultSpec);
        builder2.argumentSpec(argumentSpec);

        PythonContext context1 = builder1.build();
        PythonContext context2 = builder2.build();

        assertThat(context1.resultSpec())
                .isSameAs(resultSpec);
        assertThat(context1.argumentSpec())
                .isEqualTo(PythonArgumentSpec.empty());

        assertThat(context2.resultSpec())
                .isEqualTo(PythonResultSpec.empty());
        assertThat(context2.argumentSpec())
                .isSameAs(argumentSpec);
    }
}
