package io.maksymuimanov.python.lifecycle;

import io.maksymuimanov.python.exception.PythonLifecycleException;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.library.PythonLibraryManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ExternalPythonLibraryInitializer implements PythonInitializer {
    private final PipManager pipManager;
    private final PythonLibraryManagement[] libraries;

    @Override
    public void initialize() {
        try {
            log.info("Starting Python libraries installation");
            for (PythonLibraryManagement library : libraries) {
                if (pipManager.exists(library)) continue;
                log.info("Installing [{}] with options {}", library.getName(), library.getOptions());
                pipManager.install(library);
                log.info("Installed [{}] successfully", library.getName());
            }
        } catch (Exception e) {
            throw new PythonLifecycleException(e);
        }
    }
}
