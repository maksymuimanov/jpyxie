package io.maksymuimanov.python;

import io.maksymuimanov.python.autoconfigure.ProcessPythonExecutorAutoConfiguration;
import io.maksymuimanov.python.executor.ProcessPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.finisher.BasicPythonProcessFinisher;
import io.maksymuimanov.python.finisher.ProcessFinisher;
import io.maksymuimanov.python.input.BasicPythonErrorProcessHandler;
import io.maksymuimanov.python.input.BasicPythonInputProcessHandler;
import io.maksymuimanov.python.starter.BasicPythonProcessStarter;
import io.maksymuimanov.python.starter.ProcessStarter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = ProcessPythonExecutorAutoConfiguration.class)
class ProcessPythonExecutorConfigurationTests {
    @Autowired
    private PythonExecutor pythonExecutor;
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(ProcessPythonExecutor.class, pythonExecutor);
        Assertions.assertInstanceOf(BasicPythonProcessStarter.class, applicationContext.getBean(ProcessStarter.class));
        Assertions.assertInstanceOf(BasicPythonInputProcessHandler.class, applicationContext.getBean("inputProcessHandler"));
        Assertions.assertInstanceOf(BasicPythonErrorProcessHandler.class, applicationContext.getBean("errorProcessHandler"));
        Assertions.assertInstanceOf(BasicPythonProcessFinisher.class, applicationContext.getBean(ProcessFinisher.class));
    }
}
