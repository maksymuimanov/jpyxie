package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.interpreter.JepInterpreterFactory;
import io.jpyxie.python.interpreter.JepInterpreterType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "spring.python.jep")
public class JepProperties {
    /**
     * Whether the JEP (Java Embedded Python) autoconfiguration is enabled.
     */
    private boolean enabled = true;
    /**
     * The type of the JEP interpreter to use.
     */
    private JepInterpreterType interpreterType = JepInterpreterFactory.DEFAULT_INTERPRETER_TYPE;
    /**
     * The absolute path to the Python library ('.../libpythonXY.so' or '.../pythonXY.dll' or etc).
     */
    private String pythonLibraryPath;
    /**
     * The absolute path to the JEP library ('.../libjep.so' or '.../jep.dll' or etc).
     */
    private String jepLibraryPath;
}
