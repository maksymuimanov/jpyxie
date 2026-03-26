package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.executor.BasicPythonOutputProcessHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.process")
public class ProcessPythonExecutorProperties {
    /**
     * Whether process execution details (input, output, errors) should be logged.
     */
    private boolean loggable = BasicPythonOutputProcessHandler.DEFAULT_LOGGABLE;
}
