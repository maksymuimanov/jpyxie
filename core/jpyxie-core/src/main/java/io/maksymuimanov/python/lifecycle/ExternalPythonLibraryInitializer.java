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
            log.info("Starting Python libraries installation for [{}] libraries", libraries.length);
            for (PythonLibraryManagement library : libraries) {
                if (pipManager.exists(library)) {
                    log.debug("Library [{}] already exists, skipping installation", library.getName());
                    continue;
                }
                pipManager.install(library);
                log.info("Installed [{}] successfully", library.getName());
            }
            log.info("Completed Python libraries installation, installed [{}] libraries", libraries.length);
        } catch (Exception e) {
            log.error("Failed to install Python libraries", e);
            throw new PythonLifecycleException(e);
        }
    }
}
