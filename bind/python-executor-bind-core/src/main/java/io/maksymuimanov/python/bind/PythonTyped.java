package io.maksymuimanov.python.bind;

import org.jspecify.annotations.Nullable;

public interface PythonTyped {
    @Nullable
    String getType();

    void setType(@Nullable String type);
}
