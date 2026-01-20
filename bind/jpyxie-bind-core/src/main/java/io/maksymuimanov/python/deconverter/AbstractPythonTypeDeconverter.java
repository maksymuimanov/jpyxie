package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonTypeDeconverter;
import io.maksymuimanov.python.script.PythonRepresentation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public abstract class AbstractPythonTypeDeconverter<R extends PythonRepresentation, P> implements PythonTypeDeconverter<R> {
    private final Map<CharSequence, P> cache;

    protected AbstractPythonTypeDeconverter() {
        this(new ConcurrentHashMap<>());
    }

    protected P getValue(CharSequence value, Function<CharSequence, P> valueMapper) {
        return this.cache.computeIfAbsent(value, valueMapper);
    }

    protected void saveValue(CharSequence value, P valueRepresentation) {
        this.cache.put(value, valueRepresentation);
    }

    public boolean matches(CharSequence value, Function<CharSequence, Boolean> outerCondition, Function<CharSequence, Boolean> innerCondition) {
        if (this.cache.containsKey(value)) {
            return true;
        } else if (outerCondition.apply(value)) {
            return innerCondition.apply(value);
        } else {
            return false;
        }
    }
}