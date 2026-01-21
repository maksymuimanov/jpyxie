package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.interpreter.JepInterpreterType;
import io.maksymuimanov.python.library.JepLibraryManagement;
import io.maksymuimanov.python.library.PythonLibraryManagement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "spring.python.executor.jep")
public class JepProperties {
    /**
     * Whether the JEP (Java Embedded Python) executor autoconfiguration is enabled.
     */
    private boolean enabled = true;
    /**
     * Configuration properties for the JEP library management.
     */
    private LibraryProperties library = new LibraryProperties();
    /**
     * The type of the JEP interpreter to use.
     */
    private JepInterpreterType interpreterType = JepInterpreterType.SHARED;

    @Getter @Setter
    public static class LibraryProperties {
        /**
         * Whether the automatic JEP library management is enabled.
         */
        private boolean enabled = true;
        /**
         * The library management object of the JEP Python library to install via pip.
         */
        private JepLibraryManagement install = new JepLibraryManagement();
        /**
         * The library management object of the JEP Python library to uninstall via pip.
         */
        private PythonLibraryManagement uninstall = new PythonLibraryManagement(JepLibraryManagement.JEP_LIBRARY_NAME);
    }
}
