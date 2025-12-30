package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.common.MapSpec;
import io.maksymuimanov.python.executor.PythonResultContainer;

import java.util.*;

public class PythonResultMap implements MapSpec<String, PythonResult<?>> {
    private final Map<String, PythonResult<?>> delegate;

    public static PythonResultMap of(PythonResultContainer container) {
        if (container.isEmpty()) return empty();
        Map<String, PythonResult<?>> results = new HashMap<>();
        container.forEach((name, value) -> {
            PythonResult<?> result = PythonResult.present(name, value);
            results.put(name, result);
        });
        return new PythonResultMap(results);
    }

    public static PythonResultMap empty() {
        return new PythonResultMap(Collections.emptyMap());
    }

    private PythonResultMap(Map<String, PythonResult<?>> delegate) {
        this.delegate = delegate;
    }

    public int size() {
        return this.delegate.size();
    }
    
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public boolean contains(String key) {
        return this.delegate.containsKey(key);
    }

    public PythonResult<?> get(String key) {
        return this.delegate.get(key);
    }
    
    public Set<String> keySet() {
        return Set.copyOf(this.delegate.keySet());
    }
    
    public Collection<PythonResult<?>> values() {
        return Set.copyOf(this.delegate.values());
    }

    public Set<Map.Entry<String, PythonResult<?>>> entrySet() {
        return Set.copyOf(this.delegate.entrySet());
    }

    @Override
    public Map<String, PythonResult<?>> toMap() {
        return Collections.unmodifiableMap(this.delegate);
    }
}