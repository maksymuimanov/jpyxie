package io.jpyxie.python.processor;

import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.file.PythonFileReader;
import io.jpyxie.python.resolver.PythonResolverHolder;
import io.jpyxie.python.script.PythonScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicPythonProcessorTest {
    @InjectMocks
    private BasicPythonProcessor processor;
    @Mock
    private PythonFileReader pythonFileReader;
    @Mock
    private PythonExecutor pythonExecutor;
    @Mock
    private PythonResolverHolder pythonResolverHolder;
    @Mock
    private PythonScript pythonScript;
    private PythonContext context;
    private boolean preResolutionCalled;
    private boolean preExecutionCalled;
    private boolean failureHandlerCalled;
    private boolean successHandlerCalled;
    @Mock
    private PythonResultMap resultMap;
    private PythonResultMap empty;

    @BeforeEach
    void setUp() {
        empty = PythonResultMap.empty();
        PythonContext.PreOperator preResolution = (s, r, a) -> {
            assertThat(s)
                    .isSameAs(pythonScript);
            preResolutionCalled = true;
        };
        PythonContext.PreOperator preExecution = (s, r, a) -> {
            assertThat(s)
                    .isSameAs(pythonScript);
            preExecutionCalled = true;
        };
        PythonContext.FailureHandler failureHandler = e -> {
            assertThat(e)
                    .isNotNull();
            failureHandlerCalled = true;

            return empty;
        };
        PythonContext.SuccessHandler successHandler = r -> {
            assertThat(r)
                    .isNotNull();
            successHandlerCalled = true;
            return r;
        };
        preResolutionCalled = false;
        preExecutionCalled = false;
        failureHandlerCalled = false;
        successHandlerCalled = false;
        context = PythonContext.builder(pythonScript)
                .beforeResolvers(preResolution)
                .beforeExecutor(preExecution)
                .onFail(failureHandler)
                .onSuccess(successHandler)
                .build();
    }

    @Test
    void process_shouldReturnResultMapFromSuccessHandler() {
        when(pythonFileReader.readScript(pythonScript))
                .thenReturn(pythonScript);
        when(pythonResolverHolder.resolveAll(pythonScript, context.argumentSpec()))
                .thenReturn(pythonScript);
        when(pythonExecutor.execute(pythonScript, context.resultSpec()))
                .thenReturn(resultMap);

        PythonResultMap result = processor.process(context);

        assertThat(result)
                .isEqualTo(resultMap);
        assertThat(preResolutionCalled)
                .isTrue();
        assertThat(preExecutionCalled)
                .isTrue();
        assertThat(failureHandlerCalled)
                .isFalse();
        assertThat(successHandlerCalled)
                .isTrue();
        verify(pythonFileReader)
                .readScript(pythonScript);
        verify(pythonResolverHolder)
                .resolveAll(pythonScript, context.argumentSpec());
        verify(pythonExecutor)
                .execute(pythonScript, context.resultSpec());
    }

    @Test
    void process_shouldReturnResultMapFromFailureHandler_whenFileReadFails() {
        doThrow(RuntimeException.class)
                .when(pythonFileReader)
                .readScript(pythonScript);

        PythonResultMap result = processor.process(context);

        assertThat(result)
                .isEqualTo(empty);
        assertThat(preResolutionCalled)
                .isFalse();
        assertThat(preExecutionCalled)
                .isFalse();
        assertThat(failureHandlerCalled)
                .isTrue();
        assertThat(successHandlerCalled)
                .isFalse();
        verify(pythonFileReader)
                .readScript(pythonScript);
        verify(pythonResolverHolder, never())
                .resolveAll(pythonScript, context.argumentSpec());
        verify(pythonExecutor, never())
                .execute(pythonScript, context.resultSpec());
    }

    @Test
    void process_shouldReturnResultMapFromFailureHandler_whenResolvingFails() {
        when(pythonFileReader.readScript(pythonScript))
                .thenReturn(pythonScript);
        doThrow(RuntimeException.class)
                .when(pythonResolverHolder)
                .resolveAll(pythonScript, context.argumentSpec());

        PythonResultMap result = processor.process(context);

        assertThat(result)
                .isEqualTo(empty);
        assertThat(preResolutionCalled)
                .isTrue();
        assertThat(preExecutionCalled)
                .isFalse();
        assertThat(failureHandlerCalled)
                .isTrue();
        assertThat(successHandlerCalled)
                .isFalse();
        verify(pythonFileReader)
                .readScript(pythonScript);
        verify(pythonResolverHolder)
                .resolveAll(pythonScript, context.argumentSpec());
        verify(pythonExecutor, never())
                .execute(pythonScript, context.resultSpec());
    }

    @Test
    void process_shouldReturnResultMapFromFailureHandler_whenExecutionFails() {
        when(pythonFileReader.readScript(pythonScript))
                .thenReturn(pythonScript);
        when(pythonResolverHolder.resolveAll(pythonScript, context.argumentSpec()))
                .thenReturn(pythonScript);
        doThrow(RuntimeException.class)
                .when(pythonExecutor)
                .execute(pythonScript, context.resultSpec());

        PythonResultMap result = processor.process(context);

        assertThat(result)
                .isEqualTo(empty);
        assertThat(preResolutionCalled)
                .isTrue();
        assertThat(preExecutionCalled)
                .isTrue();
        assertThat(failureHandlerCalled)
                .isTrue();
        assertThat(successHandlerCalled)
                .isFalse();
        verify(pythonFileReader)
                .readScript(pythonScript);
        verify(pythonResolverHolder)
                .resolveAll(pythonScript, context.argumentSpec());
        verify(pythonExecutor)
                .execute(pythonScript, context.resultSpec());
    }
}
