package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.library.PythonLibraryManagement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.pip")
public class PythonPipProperties {
    /**
     * Command used to invoke pip for managing Python packages. Typically 'python -m pip', but can point to an interpreter inside a virtual environment or a Windows launcher.
     */
    private String command = "python -m pip";
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
