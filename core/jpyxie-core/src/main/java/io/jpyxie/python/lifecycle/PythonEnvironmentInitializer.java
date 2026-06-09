package io.jpyxie.python.lifecycle;

import io.jpyxie.python.environment.PythonEnvironment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PythonEnvironmentInitializer implements PythonInitializer {
    private final PythonEnvironment pythonEnvironment;

    public PythonEnvironmentInitializer(PythonEnvironment pythonEnvironment) {
        this.pythonEnvironment = pythonEnvironment;
    }

    @Override
    public void initialize() {
        try {
            log.info("Creating Python virtual environment");
            pythonEnvironment.create();
            log.info("Python virtual environment initialized successfully: {}", pythonEnvironment.getPath());
        } catch (Exception e) {
            throw PythonEnvironmentInitializerException.failedToInitialize(e);
        }
    }

    @Override
    public int getPriority() {
        return HIGH_PRIORITY;
    }
}
