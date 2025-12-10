package io.maksymuimanov.python.lifecycle;

import io.maksymuimanov.python.exception.PythonLifecycleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JythonInitializer implements PythonInitializer {
    public static final String PYTHON_HOME = "python.home";
    public static final String PYTHON_IMPORT_SITE = "python.import.site";
    private final String pythonHome;
    private final boolean importSite;

    @Override
    public void initialize() {
        try {
            log.info("Starting Jython initialization");
            if (!pythonHome.isBlank()) System.setProperty(PYTHON_HOME, pythonHome);
            System.setProperty(PYTHON_IMPORT_SITE, String.valueOf(importSite));
            log.info("Jython initialization is finished");
        } catch (Exception e) {
            log.error("Failed to initialize Jython", e);
            throw new PythonLifecycleException(e);
        }
    }
}