package io.maksymuimanov.python.lifecycle;

import io.maksymuimanov.python.exception.PythonLifecycleException;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.library.PythonLibraryManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ExternalPythonLibraryFinalizer implements PythonFinalizer {
    private final PipManager pipManager;
    private final PythonLibraryManagement[] libraries;

    @Override
    public void finish() {
        try {
            log.info("Starting Python libraries uninstallation for [{}] libraries", libraries.length);
            for (PythonLibraryManagement library : libraries) {
                if (!pipManager.exists(library)) {
                    log.debug("Library [{}] not found, skipping uninstallation", library.getName());
                    continue;
                }
                pipManager.uninstall(library);
                log.info("Uninstalled [{}] successfully", library.getName());
            }
            log.info("Completed Python libraries uninstallation, removed [{}] libraries", libraries.length);
        } catch (Exception e) {
            log.error("Failed to uninstall Python libraries", e);
            throw new PythonLifecycleException(e);
        }
    }
}
