package io.maksymuimanov.python.bind;

import java.io.Serializable;

public interface PythonSerializable extends Serializable {
    String toPythonString();
}
