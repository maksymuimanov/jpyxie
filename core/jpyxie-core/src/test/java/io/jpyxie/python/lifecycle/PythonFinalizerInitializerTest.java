package io.jpyxie.python.lifecycle;

import io.jpyxie.python.environment.PythonEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PythonFinalizerInitializerTest {
    private PythonEnvironmentFinalizer finalizer;
    @Mock
    private PythonEnvironment environment;

    @BeforeEach
    void setUp() {
        finalizer = new PythonEnvironmentFinalizer(environment);
    }

    @Test
    void finish_shouldCallRemoveOnEnvironment() {
        finalizer.finish();

        verify(environment)
                .remove();
    }

    @Test
    void finish_shouldFail_whenExceptionThrown() {
        doThrow(RuntimeException.class)
                .when(environment)
                .remove();

        assertThatThrownBy(() -> finalizer.finish())
                .isInstanceOf(PythonLifecycleException.class);
    }
}
