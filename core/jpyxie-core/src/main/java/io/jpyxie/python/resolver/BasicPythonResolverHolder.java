package io.jpyxie.python.resolver;

import io.jpyxie.python.script.PythonScript;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class BasicPythonResolverHolder implements PythonResolverHolder {
    private final List<PythonResolver> pythonResolvers;

    public BasicPythonResolverHolder(PythonResolver pythonResolver) {
        this.pythonResolvers = List.of(pythonResolver);
    }

    public BasicPythonResolverHolder(List<PythonResolver> pythonResolvers) {
        this.pythonResolvers = pythonResolvers;
        Collections.sort(this.pythonResolvers);
    }

    @Override
    public PythonScript resolveAll(PythonScript script, PythonArgumentSpec argumentSpec) {
        String name = script.getName();
        try {
            log.debug("Resolving Python script [name: {}]", name);
            for (PythonResolver resolver : this.getResolvers()) {
                log.debug("Applying Python script [name: {}] resolver [{}]", name, resolver.getClass().getSimpleName());
                resolver.resolve(script, argumentSpec);
            }
            log.debug("Resolved Python script [name: {}]", name);
            return script;
        } catch (Exception e) {
            throw BasicPythonResolverHolderException.failedToResolve(script, e);
        }
    }

    @Override
    public List<PythonResolver> getResolvers() {
        return pythonResolvers;
    }
}