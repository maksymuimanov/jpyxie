package io.jpyxie.python;

import io.jpyxie.python.autoconfigure.ProcessPythonExecutorAutoConfiguration;
import io.jpyxie.python.executor.*;
import io.jpyxie.python.finisher.BasicPythonProcessFinisher;
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
        Assertions.assertInstanceOf(BasicPythonOutputProcessHandler.class, applicationContext.getBean("processOutputHandler"));
        Assertions.assertInstanceOf(BasicPythonErrorProcessHandler.class, applicationContext.getBean("processErrorHandler"));
        Assertions.assertInstanceOf(BasicPythonProcessFinisher.class, applicationContext.getBean(ProcessFinisher.class));
    }
}
