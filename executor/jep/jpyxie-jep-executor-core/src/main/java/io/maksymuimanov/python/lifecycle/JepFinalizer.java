package io.maksymuimanov.python.lifecycle;

import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.library.PythonLibraryManagement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JepFinalizer implements PythonFinalizer {
    private final PipManager pipManager;
    private final PythonLibraryManagement jepLibraryManagement;

    @Override
    public void finish() {
        if (!pipManager.exists(jepLibraryManagement)) return;
        pipManager.uninstall(jepLibraryManagement);
    }
}
