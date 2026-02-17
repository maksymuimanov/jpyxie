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
class ExternalPythonLibraryFinalizerTest {
    private ExternalPythonLibraryFinalizer finalizer;
    @Mock
    private PipManager pipManager;
    @Mock
    private PythonLibrary mockLibrary;

    @BeforeEach
    void setUp() {
        finalizer = new ExternalPythonLibraryFinalizer(pipManager, new PythonLibrary[]{mockLibrary});
    }

    @Test
    void finish_shouldUninstallLibrary_whenExists() {
        when(pipManager.exists(mockLibrary))
                .thenReturn(true);

        finalizer.finish();

        verify(pipManager)
                .exists(mockLibrary);
        verify(pipManager)
                .uninstall(mockLibrary);
    }

    @Test
    void finish_shouldIgnoreLibrary_whenNotExists() {
        when(pipManager.exists(mockLibrary))
                .thenReturn(false);

        finalizer.finish();

        verify(pipManager)
                .exists(mockLibrary);
        verify(pipManager, never())
                .uninstall(mockLibrary);
    }

    @Test
    void finish_shouldFail_whenExceptionThrown() {
        doThrow(new RuntimeException())
                .when(pipManager)
                .exists(mockLibrary);

        assertThatThrownBy(() -> finalizer.finish())
                .isInstanceOf(PythonLifecycleException.class);
        verify(pipManager)
                .exists(mockLibrary);
        verify(pipManager, never())
                .uninstall(mockLibrary);
    }
}
