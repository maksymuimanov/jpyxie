package io.jpyxie.python.environment;

import io.jpyxie.python.constant.PythonConstants;
import io.jpyxie.python.exception.PythonEnvironmentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@EnabledOnOs(OS.WINDOWS)
class WindowsPythonEnvironmentTest {
    private WindowsVenvPythonEnvironment windowsVenvPythonEnvironment;

    @AfterEach
    void tearDown() {
        reset(windowsVenvPythonEnvironment);
        if (windowsVenvPythonEnvironment != null
                && windowsVenvPythonEnvironment.exists()) {
            windowsVenvPythonEnvironment.remove();
        }
    }

    @Test
    void create_shouldCreateNewVenv_whenNotExists() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        assertThatCode(() -> windowsVenvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(windowsVenvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void create_shouldNotRecreateVenv_whenExistsAndUsingSkipExistingHandler() {
        windowsVenvPythonEnvironment = createSpyEnvironment(new WindowsVenvPythonEnvironment.SkipExistingHandler());

        windowsVenvPythonEnvironment.create();

        assertThatCode(() -> windowsVenvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(windowsVenvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void create_shouldOverrideExistingVenv_whenExistsAndUsingRemoveExistingHandler() {
        windowsVenvPythonEnvironment = createSpyEnvironment(new WindowsVenvPythonEnvironment.RemoveExistingHandler());

        windowsVenvPythonEnvironment.create();

        assertThatCode(() -> windowsVenvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(windowsVenvPythonEnvironment.exists())
                .isTrue();
        verify(windowsVenvPythonEnvironment)
                .remove();
    }

    @Test
    void create_shouldThrowException_whenExistsAndUsingFailExistingHandler() {
        windowsVenvPythonEnvironment = createSpyEnvironment(new WindowsVenvPythonEnvironment.FailExistingHandler());

        windowsVenvPythonEnvironment.create();

        assertThatThrownBy(() -> windowsVenvPythonEnvironment.create())
                .isInstanceOf(PythonEnvironmentException.class);
        assertThat(windowsVenvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void getExecutableOrBackup_shouldReturnExecutable() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        windowsVenvPythonEnvironment.create();

        assertThat(windowsVenvPythonEnvironment.getExecutableOrBackup())
                .contains(PythonConstants.PYTHON)
                .isNotEqualTo(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrBackup_shouldReturnBackup() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        assertThat(windowsVenvPythonEnvironment.getExecutableOrBackup())
                .isEqualTo(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrElse_shouldReturnExecutable() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        windowsVenvPythonEnvironment.create();

        assertThat(windowsVenvPythonEnvironment.getExecutableOrElse("executable"))
                .contains(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrElse_shouldReturnDefinedExecutable_whenVenvDoesNotExist() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        assertThat(windowsVenvPythonEnvironment.getExecutableOrElse("executable"))
                .isEqualTo("executable");
    }

    @Test
    void getExecutable_shouldReturnScriptsPythonExe_whenWindows() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        windowsVenvPythonEnvironment.create();

        assertThat(windowsVenvPythonEnvironment.getExecutable())
                .contains(WindowsVenvPythonEnvironment.SCRIPTS_DIRECTORY)
                .contains(WindowsVenvPythonEnvironment.PYTHON_EXE);
    }

    @Test
    void getExecutable_shouldThrowException_whenVenvDoesNotExist() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> windowsVenvPythonEnvironment.getExecutable())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void getPathOrElse_shouldReturnVenvPath() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        windowsVenvPythonEnvironment.create();

        assertThat(windowsVenvPythonEnvironment.getPathOrElse(Path.of(".")))
                .isEqualTo(windowsVenvPythonEnvironment.getPath());
    }

    @Test
    void getPathOrElse_shouldReturnDefinedVenvPath_whenVenvDoesNotExist() {
        windowsVenvPythonEnvironment = createSpyEnvironment();
        Path path = mock(Path.class);

        assertThat(windowsVenvPythonEnvironment.getPathOrElse(path))
                .isEqualTo(path);
    }

    @Test
    void getPath_shouldReturnVenvPath() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        windowsVenvPythonEnvironment.create();

        assertThat(windowsVenvPythonEnvironment.getPath())
                .exists();
    }

    @Test
    void getPath_shouldThrowException_whenVenvPathIsNull() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> windowsVenvPythonEnvironment.getPath())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void remove_shouldRemoveVenv() {
        windowsVenvPythonEnvironment = createSpyEnvironment();
        windowsVenvPythonEnvironment.create();

        windowsVenvPythonEnvironment.remove();

        assertThat(windowsVenvPythonEnvironment.exists())
                .isFalse();
    }

    @Test
    void remove_shouldThrowException_whenVenvDoesNotExist() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> windowsVenvPythonEnvironment.remove())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void remove_shouldWrapAndThrowException_whenThrownException() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        doThrow(RuntimeException.class)
                .when(windowsVenvPythonEnvironment)
                .exists();

        assertThatThrownBy(() -> windowsVenvPythonEnvironment.remove())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void exists_shouldReturnTrue_whenVenvExists() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        windowsVenvPythonEnvironment.create();

        assertThat(windowsVenvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void exists_shouldReturnFalse_whenVenvDoesNotExist() {
        windowsVenvPythonEnvironment = createSpyEnvironment();

        assertThat(windowsVenvPythonEnvironment.exists())
                .isFalse();
    }

    private static WindowsVenvPythonEnvironment createSpyEnvironment() {
        return createSpyEnvironment(new WindowsVenvPythonEnvironment.SkipExistingHandler());
    }

    private static WindowsVenvPythonEnvironment createSpyEnvironment(PythonEnvironment.OnExistingHandler onExistingHandler) {
        return spy(new WindowsVenvPythonEnvironment(PythonConstants.PYTHON, onExistingHandler));
    }
}