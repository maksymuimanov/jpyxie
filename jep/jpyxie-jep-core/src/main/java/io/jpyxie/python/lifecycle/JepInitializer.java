package io.jpyxie.python.lifecycle;

import io.jpyxie.python.exception.PythonLifecycleException;
import io.jpyxie.python.library.JepLibraryManagement;
import io.jpyxie.python.library.PipManager;
import jep.MainInterpreter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RequiredArgsConstructor
public class JepInitializer implements PythonInitializer {
    private final PipManager pipManager;
    private final JepLibraryManagement jepLibraryManagement;

    @Override
    public void initialize() {
        try {
            log.info("Starting JEP initialization");
            if (!pipManager.exists(jepLibraryManagement)) {
                log.info("JEP library not found. Installing via PipManager");
                pipManager.install(jepLibraryManagement);
            } else {
                log.info("JEP library already installed");
            }
            String pythonExecutablePath = jepLibraryManagement.getPythonExecutablePath();
            String jepExecutablePath = jepLibraryManagement.getJepExecutablePath();
            if (pythonExecutablePath != null
                    && jepExecutablePath != null
                    && Files.exists(Path.of(pythonExecutablePath))
                    && Files.exists(Path.of(jepExecutablePath))) {
                log.info("Loading Python executable from: {}", pythonExecutablePath);
                System.load(pythonExecutablePath);
                log.info("Setting JEP library path: {}", jepExecutablePath);
                MainInterpreter.setJepLibraryPath(jepExecutablePath);
                log.info("JEP successfully initialized");
            } else if (pythonExecutablePath != null) {
                log.error("Invalid Python executable path: {}", pythonExecutablePath);
                throw new PythonLifecycleException("Python executable path is not valid!");
            } else if (jepExecutablePath != null) {
                log.error("Invalid JEP executable path: {}", jepExecutablePath);
                throw new PythonLifecycleException("JEP executable path is not valid!");
            }
            log.info("JEP initialization is finished");
        } catch (Exception e) {
            log.error("Failed to initialize JEP", e);
            throw new PythonLifecycleException(e);
        }
    }
}