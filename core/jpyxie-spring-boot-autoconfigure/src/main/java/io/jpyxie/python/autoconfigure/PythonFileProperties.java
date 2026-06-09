package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.script.SpringPythonScriptFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.file")
public class PythonFileProperties {
    /**
     * Base directory path where Python scripts are stored or loaded from.
     */
    private String path = SpringPythonScriptFactory.DEFAULT_PARENT_DIRECTORY;
    /**
     * Charset used to read Python script files.
     */
    private String charset = SpringPythonScriptFactory.DEFAULT_CHARSET_NAME;
}
