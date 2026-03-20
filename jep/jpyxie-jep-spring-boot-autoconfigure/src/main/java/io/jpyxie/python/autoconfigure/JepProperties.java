package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.interpreter.JepInterpreterFactory;
import io.jpyxie.python.interpreter.JepInterpreterType;
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
     * The type of the JEP interpreter to use.
     */
    private JepInterpreterType interpreterType = JepInterpreterFactory.DEFAULT_INTERPRETER_TYPE;
}
