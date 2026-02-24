package io.jpyxie.python.lifecycle;

import io.jpyxie.python.common.Prioritized;

public interface PythonFinalizer extends Prioritized {
    void finish();
}
