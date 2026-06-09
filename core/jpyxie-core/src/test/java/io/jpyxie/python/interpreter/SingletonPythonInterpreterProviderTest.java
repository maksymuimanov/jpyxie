package io.jpyxie.python.interpreter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SingletonPythonInterpreterProviderTest {
    @InjectMocks
    private SingletonPythonInterpreterProvider<AutoCloseable> interpreterProvider;
    @Mock
    private PythonInterpreterFactory<AutoCloseable> interpreterFactory;
    @Mock
    private AutoCloseable newInterpreter;

    @Test
    @SuppressWarnings("resource")
    void acquire_shouldCreateAndReturnInterpreter_whenNotInitialized() {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);

        AutoCloseable interpreter = interpreterProvider.acquire();

        assertThat(interpreter)
                .isNotNull()
                .isEqualTo(newInterpreter);
        verify(interpreterFactory, times(1))
                .create();
    }

    @Test
    @SuppressWarnings("resource")
    void acquire_shouldReturnExistingInterpreter_whenInitialized() {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);

        AutoCloseable interpreter1 = interpreterProvider.acquire();
        AutoCloseable interpreter2 = interpreterProvider.acquire();

        assertThat(interpreter2)
                .isNotNull()
                .isEqualTo(newInterpreter)
                .isSameAs(interpreter1)
                .isEqualTo(interpreter1);
        verify(interpreterFactory, times(1))
                .create();
    }

    @Test
    @SuppressWarnings("resource")
    void acquire_shouldFail_whenClosed() throws Exception {
        interpreterProvider.close();

        assertThatThrownBy(() -> interpreterProvider.acquire())
                .isInstanceOf(PythonInterpreterProviderException.class);
        verify(interpreterFactory, never())
                .create();
    }

    @Test
    @SuppressWarnings("resource")
    void acquire_shouldFail_whenExceptionThrown() {
        doThrow(RuntimeException.class)
                .when(interpreterFactory)
                .create();

        assertThatThrownBy(() -> interpreterProvider.acquire())
                .isInstanceOf(PythonInterpreterProviderException.class);
        verify(interpreterFactory, times(1))
                .create();
    }

    @Test
    void close_shouldCloseInterpreter() throws Exception {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);

        interpreterProvider.acquire();
        interpreterProvider.close();

        verify(newInterpreter, times(1))
                .close();
    }

    @Test
    void close_shouldIgnore_whenClosed() throws Exception {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);

        interpreterProvider.acquire();
        interpreterProvider.close();
        interpreterProvider.close();

        verify(newInterpreter, times(1))
                .close();
    }

    @Test
    void close_shouldIgnore_whenNull() throws Exception {
        interpreterProvider.close();

        verify(newInterpreter, never())
                .close();
    }

    @Test
    void close_shouldFail_whenExceptionThrown() throws Exception {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);

        interpreterProvider.acquire();

        doThrow(RuntimeException.class)
                .when(newInterpreter)
                .close();

        assertThatThrownBy(() -> interpreterProvider.close())
                .isInstanceOf(PythonInterpreterProviderException.class);
        verify(newInterpreter, times(1))
                .close();
    }
}
