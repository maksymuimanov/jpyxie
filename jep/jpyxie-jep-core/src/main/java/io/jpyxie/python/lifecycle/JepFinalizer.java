package io.jpyxie.python.lifecycle;

import io.jpyxie.python.library.PythonLibrary;
import io.jpyxie.python.library.PythonLibraryManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JepFinalizer implements PythonFinalizer {
    private final PythonLibraryManager pythonLibraryManager;
    private final PythonLibrary jepLibraryManagement;

    @Override
    public void finish() {
        if (!pythonLibraryManager.exists(jepLibraryManagement)) return;
        pythonLibraryManager.uninstall(jepLibraryManagement);
    }
}
