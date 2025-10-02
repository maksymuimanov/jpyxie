package io.w4t3rcs.python;

import io.w4t3rcs.python.config.PythonAutoConfiguration;
import io.w4t3rcs.python.file.BasicPythonFileReader;
import io.w4t3rcs.python.file.PythonFileReader;
import io.w4t3rcs.python.processor.BasicPythonProcessor;
import io.w4t3rcs.python.processor.PythonProcessor;
import io.w4t3rcs.python.resolver.BasicPythonResolverHolder;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = {PythonAutoConfiguration.class})
class DefaultPythonAutoConfigurationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(BasicPythonFileReader.class, applicationContext.getBean(PythonFileReader.class));
        Assertions.assertInstanceOf(BasicPythonResolverHolder.class, applicationContext.getBean(PythonResolverHolder.class));
        Assertions.assertInstanceOf(BasicPythonProcessor.class, applicationContext.getBean(PythonProcessor.class));
        Assertions.assertFalse(applicationContext.containsBean("gatewayServer"));
    }
}
