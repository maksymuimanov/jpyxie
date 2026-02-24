package io.jpyxie.python.lifecycle;

import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.exception.PythonLifecycleException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PythonEnvironmentInitializer implements PythonInitializer {
    private final PythonEnvironment pythonEnvironment;

    public PythonEnvironmentInitializer(PythonEnvironment pythonEnvironment) {
        this.pythonEnvironment = pythonEnvironment;
    }

    @Override
    public void initialize() {
        log.debug("Initializing Python virtual environment");
        try {
            pythonEnvironment.create();
            log.info("Python virtual environment initialized successfully: {}", pythonEnvironment.getPath());
        } catch (Exception e) {
            log.error("Failed to initialize Python virtual environment", e);
            throw new PythonLifecycleException(e);
        }
    }
}
