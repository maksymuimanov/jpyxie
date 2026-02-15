package io.jpyxie.python.script;

@SuppressWarnings("LombokGetterMayBeUsed")
public abstract class PythonValueContainer<T> implements PythonRepresentation {
    private final T value;

    public PythonValueContainer(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
