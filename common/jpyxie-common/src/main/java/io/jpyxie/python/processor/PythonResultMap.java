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

public record PythonResultMap(
        Map<String, PythonResult<?>> delegate
) implements MapSpec<String, PythonResult<?>> {
    private static final PythonResultMap EMPTY = new PythonResultMap(Collections.emptyMap());

    public static PythonResultMap of() {
        return new PythonResultMap(new HashMap<>());
    }

    public static PythonResultMap of(PythonResultSpec resultSpec, Function<PythonResultRequirement<?>, @Nullable Object> function) {
        if (resultSpec.isEmpty()) return empty();
        Map<String, PythonResult<?>> results = new HashMap<>();
        resultSpec.forEach(entry -> {
            String name = entry.getKey();
            PythonResultRequirement<?> requirement = entry.getValue();
            Object value = function.apply(requirement);
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
        return EMPTY;
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

    public <R> R get(String name, Class<R> clazz) {
        PythonResult<?> pythonResult = this.get(name);
        boolean isAssignable = clazz.isAssignableFrom(pythonResult.type());
        if (isAssignable) {
            return clazz.cast(pythonResult.body());
        }
        throw new PythonProcessionException("Cannot cast " + pythonResult.type() + " to " + clazz);
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
}