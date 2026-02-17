package io.jpyxie.python.lifecycle;

import io.jpyxie.python.library.PipManager;
import io.jpyxie.python.library.PythonLibrary;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JepFinalizer implements PythonFinalizer {
    private final PipManager pipManager;
    private final PythonLibrary jepLibraryManagement;

    @Override
    public void finish() {
        if (!pipManager.exists(jepLibraryManagement)) return;
        pipManager.uninstall(jepLibraryManagement);
    }
}
