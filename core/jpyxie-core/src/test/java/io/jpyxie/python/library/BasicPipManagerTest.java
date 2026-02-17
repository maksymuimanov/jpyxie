package io.jpyxie.python.library;

import io.jpyxie.python.exception.PythonLibraryManagementException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BasicPipManagerTest {
    private final BasicPipManager pipManager = new BasicPipManager();
    private final BasicPipManager unavailablePipManager = new BasicPipManager("PipIsUnavailable");

    @Test
    @Order(3)
    void exists_shouldReturnTrue_whenLibraryInstalled() {
        PythonLibrary existingLibrary = spy(new PythonLibrary("numpy"));

        boolean exists = pipManager.exists(existingLibrary);

        assertThat(exists)
                .isTrue();
    }

    @Test
    @Order(4)
    void exists_shouldReturnFalse_whenLibraryNotInstalled() {
        PythonLibrary nonExistingLibrary = spy(new PythonLibrary("django"));

        boolean exists = pipManager.exists(nonExistingLibrary);

        assertThat(exists)
                .isFalse();
    }

    @Test
    @Order(5)
    void exists_shouldFail_whenPipUnavailable() {
        PythonLibrary library = spy(new PythonLibrary("numpy"));

        assertThatThrownBy(() -> unavailablePipManager.exists(library))
                .isInstanceOf(PythonLibraryManagementException.class);
    }

    @Test
    @Order(2)
    void install_shouldProcess() {
        PythonLibrary library = spy(new PythonLibrary("numpy"));

        pipManager.install(library);

        assertThat(pipManager.exists(library))
                .isTrue();
    }

    @Test
    @Order(1)
    void install_shouldFail_whenPipUnavailable() {
        PythonLibrary library = spy(new PythonLibrary("numpy"));

        assertThatThrownBy(() -> unavailablePipManager.install(library))
                .isInstanceOf(PythonLibraryManagementException.class);
        assertThat(pipManager.exists(library))
                .isFalse();
    }

    @Test
    @Order(7)
    void uninstall_shouldProcess() {
        PythonLibrary library = spy(new PythonLibrary("numpy"));

        pipManager.uninstall(library);

        assertThat(pipManager.exists(library))
                .isFalse();
    }

    @Test
    @Order(6)
    void uninstall_shouldFail_whenPipUnavailable() {
        PythonLibrary library = spy(new PythonLibrary("numpy"));

        assertThatThrownBy(() -> unavailablePipManager.install(library))
                .isInstanceOf(PythonLibraryManagementException.class);
        assertThat(pipManager.exists(library))
                .isTrue();
    }
}
