package io.jpyxie.python.lifecycle;

import io.jpyxie.python.library.PipManager;
import io.jpyxie.python.library.PythonLibraryManagement;
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
