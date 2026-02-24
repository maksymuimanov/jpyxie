package io.jpyxie.python.environment;

import io.jpyxie.python.constant.PythonConstants;
import io.jpyxie.python.exception.PythonEnvironmentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class VenvPythonEnvironmentTest {
    private VenvPythonEnvironment venvPythonEnvironment;

    @AfterEach
    void tearDown() {
        reset(venvPythonEnvironment);
        if (venvPythonEnvironment != null
                && venvPythonEnvironment.exists()) {
            venvPythonEnvironment.remove();
        }
    }

    @Test
    void create_shouldCreateNewVenv_whenNotExists() {
        venvPythonEnvironment = createSpyEnvironment();

        assertThatCode(() -> venvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(venvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void create_shouldNotRecreateVenv_whenExistsAndUsingSkipExistingHandler() {
        venvPythonEnvironment = createSpyEnvironment(new VenvPythonEnvironment.SkipExistingHandler());

        venvPythonEnvironment.create();

        assertThatCode(() -> venvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(venvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void create_shouldOverrideExistingVenv_whenExistsAndUsingRemoveExistingHandler() {
        venvPythonEnvironment = createSpyEnvironment(new VenvPythonEnvironment.RemoveExistingHandler());

        venvPythonEnvironment.create();

        assertThatCode(() -> venvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(venvPythonEnvironment.exists())
                .isTrue();
        verify(venvPythonEnvironment)
                .remove();
    }

    @Test
    void create_shouldThrowException_whenExistsAndUsingFailExistingHandler() {
        venvPythonEnvironment = createSpyEnvironment(new VenvPythonEnvironment.FailExistingHandler());

        venvPythonEnvironment.create();

        assertThatThrownBy(() -> venvPythonEnvironment.create())
                .isInstanceOf(PythonEnvironmentException.class);
        assertThat(venvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void getExecutableOrBackup_shouldReturnExecutable() {
        venvPythonEnvironment = createSpyEnvironment();

        venvPythonEnvironment.create();

        assertThat(venvPythonEnvironment.getExecutableOrBackup())
                .contains(PythonConstants.PYTHON)
                .isNotEqualTo(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrBackup_shouldReturnBackup() {
        venvPythonEnvironment = createSpyEnvironment();

        assertThat(venvPythonEnvironment.getExecutableOrBackup())
                .isEqualTo(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrElse_shouldReturnExecutable() {
        venvPythonEnvironment = createSpyEnvironment();

        venvPythonEnvironment.create();

        assertThat(venvPythonEnvironment.getExecutableOrElse("executable"))
                .contains(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrElse_shouldReturnDefinedExecutable_whenVenvDoesNotExist() {
        venvPythonEnvironment = createSpyEnvironment();

        assertThat(venvPythonEnvironment.getExecutableOrElse("executable"))
                .isEqualTo("executable");
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void getExecutable_shouldReturnBinPython_whenLinux() {
        venvPythonEnvironment = createSpyEnvironment();

        venvPythonEnvironment.create();

        assertThat(venvPythonEnvironment.getExecutable())
                .contains(VenvPythonEnvironment.BIN_DIRECTORY)
                .contains(PythonConstants.PYTHON);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void getExecutable_shouldReturnScriptsPythonExe_whenWindows() {
        venvPythonEnvironment = createSpyEnvironment();

        venvPythonEnvironment.create();

        assertThat(venvPythonEnvironment.getExecutable())
                .contains(VenvPythonEnvironment.SCRIPTS_DIRECTORY)
                .contains(VenvPythonEnvironment.PYTHON_EXE);
    }

    @Test
    void getExecutable_shouldThrowException_whenVenvDoesNotExist() {
        venvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> venvPythonEnvironment.getExecutable())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void getPath_shouldReturnVenvPath() {
        venvPythonEnvironment = createSpyEnvironment();

        venvPythonEnvironment.create();

        assertThat(venvPythonEnvironment.getPath())
                .exists();
    }

    @Test
    void getPath_shouldThrowException_whenVenvPathIsNull() {
        venvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> venvPythonEnvironment.getPath())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void remove_shouldRemoveVenv() {
        venvPythonEnvironment = createSpyEnvironment();
        venvPythonEnvironment.create();

        venvPythonEnvironment.remove();

        assertThat(venvPythonEnvironment.exists())
                .isFalse();
    }

    @Test
    void remove_shouldThrowException_whenVenvDoesNotExist() {
        venvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> venvPythonEnvironment.remove())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void remove_shouldWrapAndThrowException_whenThrownException() {
        venvPythonEnvironment = createSpyEnvironment();

        doThrow(RuntimeException.class)
                .when(venvPythonEnvironment)
                .exists();

        assertThatThrownBy(() -> venvPythonEnvironment.remove())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void exists_shouldReturnTrue_whenVenvExists() {
        venvPythonEnvironment = createSpyEnvironment();

        venvPythonEnvironment.create();

        assertThat(venvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void exists_shouldReturnFalse_whenVenvDoesNotExist() {
        venvPythonEnvironment = createSpyEnvironment();

        assertThat(venvPythonEnvironment.exists())
                .isFalse();
    }

    private static VenvPythonEnvironment createSpyEnvironment() {
        return createSpyEnvironment(new VenvPythonEnvironment.SkipExistingHandler());
    }

    private static VenvPythonEnvironment createSpyEnvironment(PythonEnvironment.OnExistingHandler onExistingHandler) {
        return spy(new VenvPythonEnvironment(PythonConstants.PYTHON, onExistingHandler));
    }
}