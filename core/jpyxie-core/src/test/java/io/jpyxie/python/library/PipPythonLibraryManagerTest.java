package io.jpyxie.python.library;

import io.jpyxie.python.environment.PythonEnvironment;
import org.junit.jupiter.api.*;

import static io.jpyxie.python.PythonConstants.PYTHON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PipPythonLibraryManagerTest {
    private PipPythonLibraryManager pipManager;

    @BeforeEach
    void setUp() {
        PythonEnvironment environment = mock(PythonEnvironment.class);

        when(environment.getExecutableOrBackup())
                .thenReturn(PYTHON);
        pipManager = new PipPythonLibraryManager(environment);
    }

    @Test
    @Order(2)
    void exists_shouldReturnTrue_whenLibraryInstalled() {
        PythonLibrary existingLibrary = spy(new PythonLibrary("numpy"));

        boolean exists = pipManager.exists(existingLibrary);

        assertThat(exists)
                .isTrue();
    }

    @Test
    @Order(3)
    void exists_shouldReturnFalse_whenLibraryNotInstalled() {
        PythonLibrary nonExistingLibrary = spy(new PythonLibrary("django"));

        boolean exists = pipManager.exists(nonExistingLibrary);

        assertThat(exists)
                .isFalse();
    }

    @Test
    @Order(1)
    void install_shouldProcess() {
        PythonLibrary library = spy(new PythonLibrary("numpy"));

        pipManager.install(library);

        assertThat(pipManager.exists(library))
                .isTrue();
    }

    @Test
    @Order(4)
    void uninstall_shouldProcess() {
        PythonLibrary library = spy(new PythonLibrary("numpy"));

        pipManager.uninstall(library);

        assertThat(pipManager.exists(library))
                .isFalse();
    }
}
