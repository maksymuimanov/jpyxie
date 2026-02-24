package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.constant.PythonConstants;
import io.jpyxie.python.environment.VenvPythonEnvironment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter @Setter
@ConfigurationProperties("spring.python.environment")
public class PythonEnvironmentProperties {
    /**
     * Whether to enable creating a virtual environment.
     */
    private boolean enabled = true;
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
    private String parentDirectory = VenvPythonEnvironment.DEFAULT_VENV_PARENT_DIRECTORY;
    /**
     * Whether to redirect error stream when executing virtual environment commands.
     */
    private boolean redirectErrorStream = VenvPythonEnvironment.DEFAULT_REDIRECT_ERROR_STREAM;
    /**
     * Whether to redirect output stream when executing virtual environment commands.
     */
    private boolean redirectOutputStream = VenvPythonEnvironment.DEFAULT_REDIRECT_OUTPUT_STREAM;
    /**
     * Whether to read the output of virtual environment commands.
     */
    private boolean readOutput = VenvPythonEnvironment.DEFAULT_READ_OUTPUT;
    /**
     * Timeout for executing virtual environment commands.
     */
    private Duration timeout = VenvPythonEnvironment.DEFAULT_TIMEOUT;

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
