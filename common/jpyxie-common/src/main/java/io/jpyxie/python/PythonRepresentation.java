package io.jpyxie.python;

import java.io.Serializable;

public interface PythonRepresentation extends Serializable {
    String toPythonString();
}
