package io.jpyxie.python.lifecycle;

import io.jpyxie.python.exception.PythonLifecycleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JythonInitializer implements PythonInitializer {
    public static final String PYTHON_HOME = "python.home";
    public static final String PYTHON_IMPORT_SITE = "python.import.site";
    public static final boolean DEFAULT_IMPORT_SITE = false;
    private final String pythonHome;
    private final boolean importSite;

    public JythonInitializer(String pythonHome) {
        this(pythonHome, DEFAULT_IMPORT_SITE);
    }

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