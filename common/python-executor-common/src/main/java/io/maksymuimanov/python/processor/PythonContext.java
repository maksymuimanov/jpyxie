package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.exception.PythonProcessionException;
import io.maksymuimanov.python.executor.PythonResultSpec;
import io.maksymuimanov.python.resolver.PythonArgumentSpec;
import io.maksymuimanov.python.script.PythonScript;

public record PythonContext(PythonScript script,
                            PythonResultSpec resultSpec,
                            PythonArgumentSpec argumentSpec,
                            PreOperator preResolution,
                            PreOperator preExecution,
                            SuccessHandler successHandler,
                            FailureHandler failureHandler) {
    public static PythonContext.Builder builder(PythonScript script) {
        return new Builder(script);
    }

    public static final class Builder {
        private final PythonScript script;
        private PythonResultSpec resultSpec;
        private PythonArgumentSpec argumentSpec;
        private PreOperator preResolution;
        private PreOperator preExecution;
        private SuccessHandler successHandler;
        private FailureHandler failureHandler;

        private Builder(PythonScript script) {
            this.script = script;
            this.resultSpec = PythonResultSpec.create();
            this.argumentSpec = PythonArgumentSpec.create();
            this.preResolution = (s, r, a) -> {};
            this.preExecution = (s, r, a) -> {};
            this.successHandler = r -> r;
            this.failureHandler = t -> {throw new PythonProcessionException(t);};
        }

        public Builder resultSpec(PythonResultSpec resultSpec) {
            this.resultSpec = resultSpec;
            return this;
        }

        public Builder argumentSpec(PythonArgumentSpec argumentSpec) {
            this.argumentSpec = argumentSpec;
            return this;
        }

        public Builder preResolution(PreOperator preResolution) {
            this.preResolution = preResolution;
            return this;
        }

        public Builder preExecution(PreOperator preExecution) {
            this.preExecution = preExecution;
            return this;
        }

        public Builder onSuccess(SuccessHandler successHandler) {
            this.successHandler = successHandler;
            return this;
        }

        public Builder onFail(FailureHandler failureHandler) {
            this.failureHandler = failureHandler;
            return this;
        }

        public PythonContext build() {
            return new PythonContext(this.script, this.resultSpec, this.argumentSpec, this.preResolution, this.preExecution, this.successHandler, this.failureHandler);
        }
    }

    @FunctionalInterface
    public interface PreOperator {
        void operate(PythonScript script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec);
    }

    @FunctionalInterface
    public interface SuccessHandler {
        PythonResultMap onSuccess(PythonResultMap resultMap);
    }

    @FunctionalInterface
    public interface FailureHandler {
        PythonResultMap onFail(Throwable throwable);
    }
}