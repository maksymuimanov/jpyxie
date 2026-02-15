package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.library.BasicPipManager;
import io.jpyxie.python.library.PythonLibraryManagement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter @Setter
@ConfigurationProperties("spring.python.pip")
public class PythonPipProperties {
    /**
     * Command used to invoke pip for managing Python packages. Typically, 'python -m pip', but can point to an interpreter inside a virtual environment or a Windows launcher.
     */
    private String command = BasicPipManager.DEFAULT_COMMAND;
    /**
     * Whether to redirect error stream when executing pip commands.
     */
    private boolean redirectErrorStream = BasicPipManager.DEFAULT_REDIRECT_ERROR_STREAM;
    /**
     * Whether to redirect output stream when executing pip commands.
     */
    private boolean redirectOutputStream = BasicPipManager.DEFAULT_REDIRECT_OUTPUT_STREAM;
    /**
     * Whether to read the output of pip commands.
     */
    private boolean readOutput = BasicPipManager.DEFAULT_READ_OUTPUT;
    /**
     * Timeout for executing pip commands.
     */
    private Duration timeout = BasicPipManager.DEFAULT_TIMEOUT;
    /**
     * Configuration group for Python library installation and uninstallation using pip.
     */
    private LibraryProperties library = new LibraryProperties();

    @Getter @Setter
    public static class LibraryProperties {
        /**
         * Enables or disables automatic Python library management on Spring application lifecycle. When true, the system can preinstall or uninstall required packages.
         */
        private boolean enabled = false;
        /**
         * List of Python libraries to install before executing any Python scripts. Each entry defines a PythonLibraryManagement object.
         */
        private PythonLibraryManagement[] installed = new PythonLibraryManagement[0];
        /**
         * List of Python libraries to uninstall before whole application shutdown. Each entry defines a PythonLibraryManagement object.
         */
        private PythonLibraryManagement[] uninstalled = new PythonLibraryManagement[0];
    }
}
