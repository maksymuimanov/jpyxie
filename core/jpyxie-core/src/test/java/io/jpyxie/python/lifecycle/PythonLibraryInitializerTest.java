package io.jpyxie.python.lifecycle;

import io.jpyxie.python.library.PythonLibrary;
import io.jpyxie.python.library.PythonLibraryManager;
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
    private PythonLibraryManager pythonLibraryManager;
    @Mock
    private PythonLibrary mockLibrary;

    @BeforeEach
    void setUp() {
        initializer = new PythonLibraryInitializer(pythonLibraryManager, new PythonLibrary[]{mockLibrary});
    }

    @Test
    void initialize_shouldInstallLibrary_whenNotExists() {
        when(pythonLibraryManager.exists(mockLibrary))
                .thenReturn(false);

        initializer.initialize();

        verify(pythonLibraryManager)
                .exists(mockLibrary);
        verify(pythonLibraryManager)
                .install(mockLibrary);
    }

    @Test
    void initialize_shouldIgnoreLibrary_whenExists() {
        when(pythonLibraryManager.exists(mockLibrary))
                .thenReturn(true);

        initializer.initialize();

        verify(pythonLibraryManager)
                .exists(mockLibrary);
        verify(pythonLibraryManager, never())
                .install(mockLibrary);
    }

    @Test
    void initialize_shouldFail_whenExceptionThrown() {
        doThrow(new RuntimeException())
                .when(pythonLibraryManager)
                .exists(mockLibrary);

        assertThatThrownBy(() -> initializer.initialize())
                .isInstanceOf(PythonLifecycleException.class);
        verify(pythonLibraryManager)
                .exists(mockLibrary);
        verify(pythonLibraryManager, never())
                .install(mockLibrary);
    }
}
