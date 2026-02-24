package io.jpyxie.python.lifecycle;

import io.jpyxie.python.common.Prioritized;

public interface PythonInitializer extends Prioritized {
    void initialize();
}
