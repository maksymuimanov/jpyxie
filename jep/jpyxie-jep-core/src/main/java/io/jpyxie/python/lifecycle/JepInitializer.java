package io.jpyxie.python.lifecycle;

import jep.MainInterpreter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JepInitializer implements PythonInitializer {
    private final String pythonLibraryPath;
    private final String jepLibraryPath;

    @Override
    public void initialize() {
        try {
            log.info("Starting JEP initialization");
            log.debug("Loading python to [{}]", this.pythonLibraryPath);
            System.load(this.pythonLibraryPath);
            log.debug("Setting jep library path to [{}]", this.jepLibraryPath);
            MainInterpreter.setJepLibraryPath(this.jepLibraryPath);
            log.info("JEP initialization is finished");
        } catch (Exception e) {
            PythonLifecycleException exception = new PythonLifecycleException("Failed to initialize JEP", e);
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Override
    public int getPriority() {
        return VERY_HIGH_PRIORITY;
    }
}