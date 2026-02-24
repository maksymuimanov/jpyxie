package io.jpyxie.python.lifecycle;

import io.jpyxie.python.exception.PythonLifecycleException;
import io.jpyxie.python.library.PipManager;
import io.jpyxie.python.library.PythonLibrary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PythonLibraryInitializerTest {
    private PythonLibraryInitializer initializer;
    @Mock
    private PipManager pipManager;
    @Mock
    private PythonLibrary mockLibrary;

    @BeforeEach
    void setUp() {
        initializer = new PythonLibraryInitializer(pipManager, new PythonLibrary[]{mockLibrary});
    }

    @Test
    void initialize_shouldInstallLibrary_whenNotExists() {
        when(pipManager.exists(mockLibrary))
                .thenReturn(false);

        initializer.initialize();

        verify(pipManager)
                .exists(mockLibrary);
        verify(pipManager)
                .install(mockLibrary);
    }

    @Test
    void initialize_shouldIgnoreLibrary_whenExists() {
        when(pipManager.exists(mockLibrary))
                .thenReturn(true);

        initializer.initialize();

        verify(pipManager)
                .exists(mockLibrary);
        verify(pipManager, never())
                .install(mockLibrary);
    }

    @Test
    void initialize_shouldFail_whenExceptionThrown() {
        doThrow(new RuntimeException())
                .when(pipManager)
                .exists(mockLibrary);

        assertThatThrownBy(() -> initializer.initialize())
                .isInstanceOf(PythonLifecycleException.class);
        verify(pipManager)
                .exists(mockLibrary);
        verify(pipManager, never())
                .install(mockLibrary);
    }
}
