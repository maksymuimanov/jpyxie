package io.jpyxie.python;

import io.jpyxie.python.autoconfigure.GrpcPythonExecutorAutoConfiguration;
import io.jpyxie.python.executor.GrpcPythonExecutor;
import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.proto.PythonServiceGrpc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = GrpcPythonExecutorAutoConfiguration.class)
class PythonExecutorConfigurationTests {
    @MockitoBean
    private PythonServiceGrpc.PythonServiceBlockingStub stub;
    @Autowired
    private PythonExecutor pythonExecutor;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(GrpcPythonExecutor.class, pythonExecutor);
    }
}
