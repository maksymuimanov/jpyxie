package io.jpyxie.python.lifecycle;

import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.exception.PythonLifecycleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PythonEnvironmentInitializerTest {
    private PythonEnvironmentInitializer initializer;
    @Mock
    private PythonEnvironment environment;

    @BeforeEach
    void setUp() {
        initializer = new PythonEnvironmentInitializer(environment);
    }

    @Test
    void initialize_shouldCallCreateOnEnvironment() {
        initializer.initialize();

        verify(environment)
                .create();
    }

    @Test
    void initialize_shouldFail_whenExceptionThrown() {
        doThrow(RuntimeException.class)
                .when(environment)
                .create();

        assertThatThrownBy(() -> initializer.initialize())
                .isInstanceOf(PythonLifecycleException.class);
    }
}
