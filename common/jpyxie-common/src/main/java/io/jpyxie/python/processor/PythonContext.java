package io.jpyxie.python.processor;

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
    private PythonScript script;
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
    private FailureHandler onFailure = t -> { throw PythonProcessionException.failedToProcess(this.script, t); };

    @FunctionalInterface
    public interface PreOperator {
        void operate(PythonScript script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec);
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