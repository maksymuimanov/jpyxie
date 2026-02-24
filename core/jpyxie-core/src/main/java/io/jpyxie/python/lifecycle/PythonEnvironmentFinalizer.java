package io.jpyxie.python.lifecycle;

import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.exception.PythonLifecycleException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PythonEnvironmentFinalizer implements PythonFinalizer {
    private final PythonEnvironment pythonEnvironment;

    public PythonEnvironmentFinalizer(PythonEnvironment pythonEnvironment) {
        this.pythonEnvironment = pythonEnvironment;
    }

    @Override
    public void finish() {
        try {
            log.info("Removing Python virtual environment: {}", pythonEnvironment.getPath());
            pythonEnvironment.remove();
            log.info("Python virtual environment removed successfully");
        } catch (Exception e) {
            log.error("Failed to finish Python virtual environment", e);
            throw new PythonLifecycleException(e);
        }
    }

    @Override
    public int getPriority() {
        return LOW_PRIORITY;
    }
}
