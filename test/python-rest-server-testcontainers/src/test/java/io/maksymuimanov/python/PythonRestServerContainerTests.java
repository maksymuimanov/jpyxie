package io.maksymuimanov.python;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;

public class PythonRestServerContainerTests {
    @Rule
    public PythonRestServerContainer pythonRestServer = new PythonRestServerContainer()
            .withAdditionalImports(new String[]{"numpy"});

    @Test
    public void testContainer() {
        pythonRestServer.start();
        Assertions.assertThat(pythonRestServer.isCreated()).isTrue();
        Assertions.assertThat(pythonRestServer.isRunning()).isTrue();
        pythonRestServer.stop();
    }
}
