package io.jpyxie.python.script;

import java.io.Serializable;

public interface PythonRepresentation extends Serializable {
    String toPythonString();
}
