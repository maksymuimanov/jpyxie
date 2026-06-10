package io.jpyxie.python.executor;

import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.processor.PythonResultMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractPythonExecutor<F> implements PythonExecutor {
    private final PythonDeserializer<F> pythonDeserializer;

    protected PythonResultMap createResultMap(PythonResultSpec resultSpec, F from) {
        return PythonResultMap.of(resultSpec, requirement ->
                this.getPythonDeserializer().deserialize(from, requirement));
    }
}
