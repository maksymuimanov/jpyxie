package io.jpyxie.python.executor;

import io.jpyxie.python.common.MapSpec;
import io.jpyxie.python.exception.PythonException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PythonResultSpec implements MapSpec<String, PythonResultRequirement<?>> {
    private static final PythonResultSpec EMPTY = new PythonResultSpec(Collections.emptyMap());
    @Getter(AccessLevel.PROTECTED)
    private final Map<String, PythonResultRequirement<?>> requirements;

    public static PythonResultSpec empty() {
        return EMPTY;
    }

    public static PythonResultSpec of(String name, Class<?> type) {
        return of().require(name, type);
    }

    public static PythonResultSpec of(PythonResultRequirement<?> resultRequirement) {
        return of().require(resultRequirement);
    }

    public static PythonResultSpec of() {
        return new PythonResultSpec(new HashMap<>());
    }

    public PythonResultRequirement<?> getRequirement(String name) {
        return this.getRequirements().compute(name, (key, value) -> {
            if (value != null) return value;
            throw new PythonException("Requirement not found: " + name);
        });
    }

    public PythonResultSpec require(String name, Class<?> type) {
        PythonResultRequirement<?> requirement = new PythonResultRequirement<>(name, type);
        return this.require(requirement);
    }
    
    public PythonResultSpec require(PythonResultRequirement<?> requirement) {
        this.getRequirements().put(requirement.name(), requirement);
        return this;
    }

    @Override
    public Map<String, PythonResultRequirement<?>> toMap() {
        return Collections.unmodifiableMap(this.getRequirements());
    }
}