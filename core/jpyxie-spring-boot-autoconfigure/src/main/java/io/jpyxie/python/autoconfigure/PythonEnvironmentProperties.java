package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.constant.PythonConstants;
import io.jpyxie.python.environment.AbstractVenvPythonEnvironment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter @Setter
@ConfigurationProperties("spring.python.environment")
public class PythonEnvironmentProperties {
    /**
     * Whether to enable virtual environment autoconfiguration.
     */
    private boolean enabled = true;
    /**
     * Whether to enable creating a virtual environment.
     */
    private boolean createOnStart = true;
    /**
     * Whether to remove the virtual environment when the application context is closed.
     */
    private boolean removeOnClose = true;
    /**
     * Global Python executable used to create virtual environments.
     */
    private String globalPythonExecutable = PythonConstants.PYTHON;
    /**
     * Backup Python executable to use when a virtual environment is not available.
     */
    private String backupPythonExecutable = PythonConstants.PYTHON;
    /**
     * Strategy for handling existing virtual environments. Options: SKIP, REMOVE, FAIL.
     */
    private OnExisting onExisting = OnExisting.SKIP;
    /**
     * Parent directory where virtual environments are created.
     */
    private String parentDirectory = AbstractVenvPythonEnvironment.VENV;
    /**
     * Whether to redirect error stream when executing virtual environment commands.
     */
    private boolean redirectErrorStream = AbstractVenvPythonEnvironment.DEFAULT_REDIRECT_ERROR_STREAM;
    /**
     * Whether to redirect output stream when executing virtual environment commands.
     */
    private boolean redirectOutputStream = AbstractVenvPythonEnvironment.DEFAULT_REDIRECT_OUTPUT_STREAM;
    /**
     * Whether to read the output of virtual environment commands.
     */
    private boolean readOutput = AbstractVenvPythonEnvironment.DEFAULT_READ_OUTPUT;
    /**
     * Timeout for executing virtual environment commands.
     */
    private Duration timeout = AbstractVenvPythonEnvironment.DEFAULT_TIMEOUT;

    /**
     * Enumeration of strategies for handling existing virtual environments.
     */
    public enum OnExisting {
        /**
         * Skip creation if virtual environment already exists.
         */
        SKIP,
        /**
         * Remove existing virtual environment and create a new one.
         */
        REMOVE,
        /**
         * Fail if virtual environment already exists.
         */
        FAIL
    }
}
