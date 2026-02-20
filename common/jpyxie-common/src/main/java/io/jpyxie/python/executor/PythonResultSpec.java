package io.jpyxie.python.executor;

import io.jpyxie.python.common.MapSpec;
import io.jpyxie.python.exception.PythonException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PythonResultSpec implements MapSpec<String, PythonResultRequirement<?>> {
    private final Map<String, PythonResultRequirement<?>> requirements;

    public static PythonResultSpec empty() {
        return new PythonResultSpec(Collections.emptyMap());
    }

    public static PythonResultSpec of(String name, Class<?> type) {
        return create().require(name, type);
    }

    public static PythonResultSpec of(PythonResultRequirement<?> resultRequirement) {
        return create().require(resultRequirement);
    }

    public static PythonResultSpec create() {
        return new PythonResultSpec(new HashMap<>());
    }

    private PythonResultSpec(Map<String, PythonResultRequirement<?>> requirements) {
        this.requirements = requirements;
    }

    public PythonResultRequirement<?> getRequirement(String name) {
        return this.getRequirements().compute(name, (k, v) -> {
            if (v != null) return v;
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

    protected Map<String, PythonResultRequirement<?>> getRequirements() {
        return requirements;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof PythonResultSpec entries)) return false;

        return this.getRequirements().equals(entries.getRequirements());
    }

    @Override
    public int hashCode() {
        return this.getRequirements().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("PythonResultSpec{");
        stringBuilder.append("requirements=").append(this.getRequirements());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
