package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.library.PythonLibraryManagement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.pip")
public class PythonPipProperties {
    private String command = "python -m pip";
    private LibraryProperties library = new LibraryProperties();

    @Getter @Setter
    public static class LibraryProperties {
        private boolean enabled = false;
        private PythonLibraryManagement[] installed = new PythonLibraryManagement[0];
        private PythonLibraryManagement[] uninstalled = new PythonLibraryManagement[0];
    }
}
