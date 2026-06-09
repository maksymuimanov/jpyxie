package io.jpyxie.python.bind.type;

import io.jpyxie.python.PythonRepresentation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class PythonValueContainer<T> implements PythonRepresentation {
    private final T value;
}
