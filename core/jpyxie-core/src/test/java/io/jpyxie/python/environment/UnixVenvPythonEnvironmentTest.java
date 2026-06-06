package io.jpyxie.python.environment;

import io.jpyxie.python.PythonConstants;
import io.jpyxie.python.exception.PythonEnvironmentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@EnabledOnOs(value = { OS.LINUX, OS.MAC })
class UnixVenvPythonEnvironmentTest {
    private UnixVenvPythonEnvironment unixVenvPythonEnvironment;

    @AfterEach
    void tearDown() {
        reset(unixVenvPythonEnvironment);
        if (unixVenvPythonEnvironment != null
                && unixVenvPythonEnvironment.exists()) {
            unixVenvPythonEnvironment.remove();
        }
    }

    @Test
    void create_shouldCreateNewVenv_whenNotExists() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        assertThatCode(() -> unixVenvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(unixVenvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void create_shouldNotRecreateVenv_whenExistsAndUsingSkipExistingHandler() {
        unixVenvPythonEnvironment = createSpyEnvironment(new AbstractVenvPythonEnvironment.SkipExistingEnvironmentHandler());

        unixVenvPythonEnvironment.create();

        assertThatCode(() -> unixVenvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(unixVenvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void create_shouldOverrideExistingVenv_whenExistsAndUsingRemoveExistingHandler() {
        unixVenvPythonEnvironment = createSpyEnvironment(new AbstractVenvPythonEnvironment.RemoveExistingEnvironmentHandler());

        unixVenvPythonEnvironment.create();

        assertThatCode(() -> unixVenvPythonEnvironment.create())
                .doesNotThrowAnyException();
        assertThat(unixVenvPythonEnvironment.exists())
                .isTrue();
        verify(unixVenvPythonEnvironment)
                .remove();
    }

    @Test
    void create_shouldThrowException_whenExistsAndUsingFailExistingHandler() {
        unixVenvPythonEnvironment = createSpyEnvironment(new AbstractVenvPythonEnvironment.FailExistingEnvironmentHandler());

        unixVenvPythonEnvironment.create();

        assertThatThrownBy(() -> unixVenvPythonEnvironment.create())
                .isInstanceOf(PythonEnvironmentException.class);
        assertThat(unixVenvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void getExecutableOrBackup_shouldReturnExecutable() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        unixVenvPythonEnvironment.create();

        assertThat(unixVenvPythonEnvironment.getExecutableOrBackup())
                .contains(PythonConstants.PYTHON)
                .isNotEqualTo(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrBackup_shouldReturnBackup() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        assertThat(unixVenvPythonEnvironment.getExecutableOrBackup())
                .isEqualTo(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrElse_shouldReturnExecutable() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        unixVenvPythonEnvironment.create();

        assertThat(unixVenvPythonEnvironment.getExecutableOrElse("executable"))
                .contains(PythonConstants.PYTHON);
    }

    @Test
    void getExecutableOrElse_shouldReturnDefinedExecutable_whenVenvDoesNotExist() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        assertThat(unixVenvPythonEnvironment.getExecutableOrElse("executable"))
                .isEqualTo("executable");
    }

    @Test
    void getExecutable_shouldReturnBinPython_whenLinux() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        unixVenvPythonEnvironment.create();

        assertThat(unixVenvPythonEnvironment.getExecutable())
                .contains(UnixVenvPythonEnvironment.BIN_DIRECTORY)
                .contains(PythonConstants.PYTHON);
    }

    @Test
    void getExecutable_shouldThrowException_whenVenvDoesNotExist() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> unixVenvPythonEnvironment.getExecutable())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void getPathOrElse_shouldReturnVenvPath() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        unixVenvPythonEnvironment.create();

        assertThat(unixVenvPythonEnvironment.getPathOrElse(Path.of(".")))
                .isEqualTo(unixVenvPythonEnvironment.getPath());
    }

    @Test
    void getPathOrElse_shouldReturnDefinedVenvPath_whenVenvDoesNotExist() {
        unixVenvPythonEnvironment = createSpyEnvironment();
        Path path = mock(Path.class);

        assertThat(unixVenvPythonEnvironment.getPathOrElse(path))
                .isEqualTo(path);
    }

    @Test
    void getPath_shouldReturnVenvPath() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        unixVenvPythonEnvironment.create();

        assertThat(unixVenvPythonEnvironment.getPath())
                .exists();
    }

    @Test
    void getPath_shouldThrowException_whenVenvPathIsNull() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> unixVenvPythonEnvironment.getPath())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void remove_shouldRemoveVenv() {
        unixVenvPythonEnvironment = createSpyEnvironment();
        unixVenvPythonEnvironment.create();

        unixVenvPythonEnvironment.remove();

        assertThat(unixVenvPythonEnvironment.exists())
                .isFalse();
    }

    @Test
    void remove_shouldThrowException_whenVenvDoesNotExist() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        assertThatThrownBy(() -> unixVenvPythonEnvironment.remove())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void remove_shouldWrapAndThrowException_whenThrownException() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        doThrow(RuntimeException.class)
                .when(unixVenvPythonEnvironment)
                .exists();

        assertThatThrownBy(() -> unixVenvPythonEnvironment.remove())
                .isInstanceOf(PythonEnvironmentException.class);
    }

    @Test
    void exists_shouldReturnTrue_whenVenvExists() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        unixVenvPythonEnvironment.create();

        assertThat(unixVenvPythonEnvironment.exists())
                .isTrue();
    }

    @Test
    void exists_shouldReturnFalse_whenVenvDoesNotExist() {
        unixVenvPythonEnvironment = createSpyEnvironment();

        assertThat(unixVenvPythonEnvironment.exists())
                .isFalse();
    }

    private static UnixVenvPythonEnvironment createSpyEnvironment() {
        return createSpyEnvironment(new AbstractVenvPythonEnvironment.SkipExistingEnvironmentHandler());
    }

    private static UnixVenvPythonEnvironment createSpyEnvironment(PythonEnvironment.ExistingEnvironmentHandler existingEnvironmentHandler) {
        return spy(new UnixVenvPythonEnvironment(PythonConstants.PYTHON, existingEnvironmentHandler));
    }
}