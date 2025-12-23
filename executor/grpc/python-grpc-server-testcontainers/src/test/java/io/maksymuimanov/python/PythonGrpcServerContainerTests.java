package io.maksymuimanov.python;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;

public class PythonGrpcServerContainerTests {
    @Rule
    public PythonGrpcServerContainer pythonGrpcServer = new PythonGrpcServerContainer()
            .withAdditionalImports(new String[]{"numpy"});

    @Test
    public void testContainer() {
        pythonGrpcServer.start();
        Assertions.assertThat(pythonGrpcServer.isCreated()).isTrue();
        Assertions.assertThat(pythonGrpcServer.isRunning()).isTrue();
        pythonGrpcServer.stop();
    }
}
