package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.common.SetSpec;
import io.maksymuimanov.python.exception.PythonException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PythonResultSpec implements SetSpec<PythonResultRequirement<?>> {
    private final Set<PythonResultRequirement<?>> requirements;

    public static PythonResultSpec empty() {
        return new PythonResultSpec(Collections.emptySet());
    }

    public static PythonResultSpec of(String name, Class<?> type) {
        return create().require(name, type);
    }

    public static PythonResultSpec create() {
        return new PythonResultSpec(new HashSet<>());
    }

    private PythonResultSpec(Set<PythonResultRequirement<?>> requirements) {
        this.requirements = requirements;
    }

    public PythonResultRequirement<?> getRequirement(String name) {
        for (PythonResultRequirement<?> requirement : this.requirements) {
            if (requirement.name().equals(name)) {
                return requirement;
            }
        }
        throw new PythonException("Requirement not found: " + name);
    }

    public PythonResultSpec require(String name, Class<?> type) {
        PythonResultRequirement<?> requirement = new PythonResultRequirement<>(name, type);
        return this.require(requirement);
    }
    
    public PythonResultSpec require(PythonResultRequirement<?> requirement) {
        this.requirements.add(requirement);
        return this;
    }

    @Override
    public Set<PythonResultRequirement<?>> toSet() {
        return Collections.unmodifiableSet(this.requirements);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("PythonResultSpec{");
        stringBuilder.append("requirements=").append(this.requirements);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
