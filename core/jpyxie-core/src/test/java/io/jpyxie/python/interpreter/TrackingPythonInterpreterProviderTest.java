package io.jpyxie.python.interpreter;

import io.jpyxie.python.exception.PythonInterpreterProvisionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingPythonInterpreterProviderTest {
    @InjectMocks
    private TrackingInterpreterProvider<AutoCloseable> interpreterProvider;
    @Mock
    private PythonInterpreterFactory<AutoCloseable> interpreterFactory;
    @Spy
    private ConcurrentLinkedQueue<AutoCloseable> interpreterQueue;
    @Mock
    private AutoCloseable newInterpreter;

    @Test
    @SuppressWarnings("resource")
    void acquire_shouldCreateAndReturnInterpreter() {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);

        AutoCloseable interpreter = interpreterProvider.acquire();

        assertThat(interpreter)
                .isNotNull()
                .isEqualTo(newInterpreter);
        assertThat(interpreterQueue)
                .containsExactly(interpreter);
        verify(interpreterFactory, times(1))
                .create();
    }

    @Test
    @SuppressWarnings("resource")
    void acquire_shouldCreateMultipleInterpreters() {
        AutoCloseable newInterpreter1 = mock(AutoCloseable.class);
        AutoCloseable newInterpreter2 = mock(AutoCloseable.class);

        when(interpreterFactory.create())
                .thenReturn(newInterpreter1, newInterpreter2);

        AutoCloseable interpreter1 = interpreterProvider.acquire();
        AutoCloseable interpreter2 = interpreterProvider.acquire();

        assertThat(interpreter1)
                .isNotNull()
                .isEqualTo(newInterpreter1);
        assertThat(interpreter2)
                .isNotNull()
                .isEqualTo(newInterpreter2)
                .isNotSameAs(interpreter1);
        assertThat(interpreterQueue)
                .containsExactly(interpreter1, interpreter2);
        verify(interpreterFactory, times(2))
                .create();
    }

    @Test
    @SuppressWarnings("resource")
    void acquire_shouldFail_whenClosed() throws Exception {
        interpreterProvider.close();

        assertThatThrownBy(() -> interpreterProvider.acquire())
                .isInstanceOf(PythonInterpreterProvisionException.class);
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
                .isInstanceOf(PythonInterpreterProvisionException.class);
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
                .isInstanceOf(PythonInterpreterProvisionException.class);
        verify(newInterpreter, times(1))
                .close();
    }
}
