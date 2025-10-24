package io.maksymuimanov.python.lifecycle;

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
        log.info("Starting Python libraries uninstallation");
        for (PythonLibraryManagement library : libraries) {
            if (!pipManager.exists(library)) continue;
            log.info("Uninstalling [{}] with options {}", library.getName(), library.getOptions());
            pipManager.uninstall(library);
            log.info("Uninstalled [{}] successfully", library.getName());
        }
    }
}
