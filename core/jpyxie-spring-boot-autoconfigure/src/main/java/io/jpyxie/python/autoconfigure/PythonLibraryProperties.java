package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.library.PythonLibrary;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.library")
public class PythonLibraryProperties {
    /**
     * Enables or disables automatic Python library management on Spring application lifecycle. When true, the system can preinstall or uninstall required packages.
     */
    private boolean enabled = false;
    /**
     * List of Python libraries to install before executing any Python scripts. Each entry defines a PythonLibraryManagement object.
     */
    private PythonLibrary[] installed = new PythonLibrary[0];
    /**
     * List of Python libraries to uninstall before whole application shutdown. Each entry defines a PythonLibraryManagement object.
     */
    private PythonLibrary[] uninstalled = new PythonLibrary[0];
}
