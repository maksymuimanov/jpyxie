package io.jpyxie.python.lifecycle;

import io.jpyxie.python.exception.PythonLifecycleException;
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
class PythonLibraryFinalizerTest {
    private PythonLibraryFinalizer finalizer;
    @Mock
    private PythonLibraryManager pythonLibraryManager;
    @Mock
    private PythonLibrary mockLibrary;

    @BeforeEach
    void setUp() {
        finalizer = new PythonLibraryFinalizer(pythonLibraryManager, new PythonLibrary[]{mockLibrary});
    }

    @Test
    void finish_shouldUninstallLibrary_whenExists() {
        when(pythonLibraryManager.exists(mockLibrary))
                .thenReturn(true);

        finalizer.finish();

        verify(pythonLibraryManager)
                .exists(mockLibrary);
        verify(pythonLibraryManager)
                .uninstall(mockLibrary);
    }

    @Test
    void finish_shouldIgnoreLibrary_whenNotExists() {
        when(pythonLibraryManager.exists(mockLibrary))
                .thenReturn(false);

        finalizer.finish();

        verify(pythonLibraryManager)
                .exists(mockLibrary);
        verify(pythonLibraryManager, never())
                .uninstall(mockLibrary);
    }

    @Test
    void finish_shouldFail_whenExceptionThrown() {
        doThrow(new RuntimeException())
                .when(pythonLibraryManager)
                .exists(mockLibrary);

        assertThatThrownBy(() -> finalizer.finish())
                .isInstanceOf(PythonLifecycleException.class);
        verify(pythonLibraryManager)
                .exists(mockLibrary);
        verify(pythonLibraryManager, never())
                .uninstall(mockLibrary);
    }
}
