package io.jpyxie.python.processor;

import io.jpyxie.python.common.MapSpec;
import io.jpyxie.python.executor.PythonResultRequirement;
import io.jpyxie.python.executor.PythonResultSpec;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class PythonResultMap implements MapSpec<String, PythonResult<?>> {
    private final Map<String, PythonResult<?>> delegate;

    public static PythonResultMap create() {
        return new PythonResultMap(new HashMap<>());
    }

    public static PythonResultMap of(PythonResultSpec resultSpec, Function<PythonResultRequirement<?>, @Nullable Object> valueFunction) {
        if (resultSpec.isEmpty()) return empty();
        Map<String, PythonResult<?>> results = new HashMap<>();
        resultSpec.forEach(entry -> {
            String name = entry.getKey();
            PythonResultRequirement<?> requirement = entry.getValue();
            Object value = valueFunction.apply(requirement);
            PythonResult<?> result = PythonResult.present(name, value);
            results.put(name, result);
        });
        return new PythonResultMap(results);
    }

    public static PythonResultMap of(Map<String, Object> objects) {
        if (objects.isEmpty()) return empty();
        Map<String, PythonResult<?>> results = new HashMap<>();
        objects.forEach((name, value) -> {
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

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public boolean contains(String name) {
        return this.delegate.containsKey(name);
    }

    //TODO
    public <R> R get(String name, Class<R> clazz) {
        PythonResult<?> pythonResult = this.get(name);
        if (clazz.isAssignableFrom(pythonResult.getType())) {
            return clazz.cast(pythonResult.getBody());
        }
        throw new ClassCastException("Cannot cast " + pythonResult.getType() + " to " + clazz);
    }

    public PythonResult<?> get(String name) {
        return this.delegate.get(name);
    }

    public void putObject(String name, @Nullable Object object) {
        PythonResult<?> result = PythonResult.present(name, object);
        this.put(name, result);
    }

    public void put(String name, PythonResult<?> result) {
        this.delegate.put(name, result);
    }
    
    public Set<String> keys() {
        return Collections.unmodifiableSet(this.delegate.keySet());
    }
    
    public Set<PythonResult<?>> values() {
        return Set.copyOf(this.delegate.values());
    }

    public Set<Map.Entry<String, PythonResult<?>>> entries() {
        return Collections.unmodifiableSet(this.delegate.entrySet());
    }

    @Override
    public Map<String, PythonResult<?>> toMap() {
        return Collections.unmodifiableMap(this.delegate);
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof PythonResultMap entries)) return false;

        return this.delegate.equals(entries.delegate);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public String toString() {
        return this.values().toString();
    }
}