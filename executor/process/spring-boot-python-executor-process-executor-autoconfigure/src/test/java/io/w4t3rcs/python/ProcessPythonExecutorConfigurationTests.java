package io.w4t3rcs.python;

import io.w4t3rcs.python.config.ProcessPythonExecutorAutoConfiguration;
import io.w4t3rcs.python.executor.ProcessPythonExecutor;
import io.w4t3rcs.python.executor.PythonExecutor;
import io.w4t3rcs.python.finisher.BasicPythonProcessFinisher;
import io.w4t3rcs.python.finisher.ProcessFinisher;
import io.w4t3rcs.python.input.BasicPythonErrorProcessHandler;
import io.w4t3rcs.python.input.BasicPythonInputProcessHandler;
import io.w4t3rcs.python.starter.BasicPythonProcessStarter;
import io.w4t3rcs.python.starter.ProcessStarter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = {ProcessPythonExecutorAutoConfiguration.class})
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
