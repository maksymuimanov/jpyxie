package io.jpyxie.python.lifecycle;

import io.jpyxie.python.library.PythonLibrary;
import io.jpyxie.python.library.PythonLibraryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PythonLibraryFinalizer implements PythonFinalizer {
    private final PythonLibraryManager pythonLibraryManager;
    private final PythonLibrary[] libraries;

    @Override
    public void finish() {
        try {
            log.info("Starting Python libraries uninstallation for [{}] libraries", libraries.length);
            for (PythonLibrary library : libraries) {
                if (!pythonLibraryManager.exists(library)) {
                    log.debug("Library [{}] not found, skipping uninstallation", library.getName());
                    continue;
                }
                pythonLibraryManager.uninstall(library);
                log.info("Uninstalled [{}] successfully", library.getName());
            }
            log.info("Completed Python libraries uninstallation, removed [{}] libraries", libraries.length);
        } catch (Exception e) {
            throw PythonLibraryFinalizerException.failedToFinalize(e);
        }
    }
}
