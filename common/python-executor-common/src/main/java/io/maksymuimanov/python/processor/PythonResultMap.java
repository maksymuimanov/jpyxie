package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.common.MapSpec;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PythonResultMap implements MapSpec<String, PythonResult<?>> {
    private final Map<String, PythonResult<?>> delegate;

    public static PythonResultMap create() {
        return new PythonResultMap(new HashMap<>());
    }

    public static <F> PythonResultMap of(Map<String, Class<?>> requirements, PythonDeserializer<F> deserializer, Function<String, F> fromFunction) {
        return of(requirements, (name, type) -> {
            F from = fromFunction.apply(name);
            return deserializer.deserialize(from, type);
        });
    }

    public static PythonResultMap of(Map<String, Class<?>> requirements, BiFunction<String, Class<?>, @Nullable Object> valueFunction) {
        if (requirements.isEmpty()) return empty();
        Map<String, PythonResult<?>> results = new HashMap<>();
        requirements.forEach((name, type) -> {
            Object value = valueFunction.apply(name, type);
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

    public PythonResult<?> get(String name) {
        return this.delegate.get(name);
    }

    public void putObject(String name, Object object) {
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
        return Collections.unmodifiableSet((Set<? extends PythonResult<?>>) this.delegate.values());
    }

    public Set<Map.Entry<String, PythonResult<?>>> entries() {
        return Collections.unmodifiableSet(this.delegate.entrySet());
    }

    @Override
    public Map<String, PythonResult<?>> toMap() {
        return Collections.unmodifiableMap(this.delegate);
    }
}