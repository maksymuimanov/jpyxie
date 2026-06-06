package io.jpyxie.python.processor;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.exception.PythonProcessionException;
import io.jpyxie.python.executor.PythonResultSpec;
import io.jpyxie.python.resolver.PythonArgumentSpec;
import io.jpyxie.python.script.PythonScript;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PythonContext {
    private PythonRepresentation script;
    @Builder.Default
    private PythonResultSpec resultSpec = PythonResultSpec.empty();
    @Builder.Default
    private PythonArgumentSpec argumentSpec = PythonArgumentSpec.empty();
    @Builder.Default
    private PreOperator beforeResolvers = (s, r, a) -> {};
    @Builder.Default
    private PreOperator beforeExecutor = (s, r, a) -> {};
    @Builder.Default
    private SuccessHandler onSuccess = r -> r;
    @Builder.Default
    private FailureHandler onFail = t -> { throw new PythonProcessionException(t); };

    @FunctionalInterface
    public interface PreOperator {
        void operate(PythonRepresentation script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec);
    }

    @FunctionalInterface
    public interface SuccessHandler {
        PythonResultMap handleSuccess(PythonResultMap resultMap);
    }

    @FunctionalInterface
    public interface FailureHandler {
        PythonResultMap handleFailure(Throwable throwable);
    }
}