package io.jpyxie.python.lifecycle;

import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PythonInterpreterProviderFinalizerTest {
    @InjectMocks
    private PythonInterpreterProviderFinalizer finalizer;
    @Mock
    private PythonInterpreterProvider<?> provider;

    @Test
    void finish_shouldCloseProvider() throws Exception {
        assertThatCode(finalizer::finish)
                .doesNotThrowAnyException();
        verify(provider)
                .close();
    }

    @Test
    void finish_shouldFail_whenExceptionThrown() throws Exception {
        doThrow(RuntimeException.class)
                .when(provider)
                .close();
        assertThatThrownBy(() -> finalizer.finish())
                .isInstanceOf(PythonLifecycleException.class);
    }
}
