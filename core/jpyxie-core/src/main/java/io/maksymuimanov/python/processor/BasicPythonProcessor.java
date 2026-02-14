package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultSpec;
import io.maksymuimanov.python.file.PythonFileReader;
import io.maksymuimanov.python.resolver.PythonArgumentSpec;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BasicPythonProcessor implements PythonProcessor {
    private final PythonFileReader pythonFileReader;
    private final PythonExecutor pythonExecutor;
    private final PythonResolverHolder pythonResolverHolder;

    @Override
    public PythonResultMap process(PythonContext context) {
        PythonScript script = context.script();
        String name = script.getName();
        try {
            log.debug("Processing Python script [name: {}]", name);
            PythonResultSpec resultSpec = context.resultSpec();
            PythonArgumentSpec argumentSpec = context.argumentSpec();
            pythonFileReader.readScript(script);
            PythonContext.PreOperator preResolution = context.preResolution();
            preResolution.operate(script, resultSpec, argumentSpec);
            this.pythonResolverHolder.resolveAll(script, argumentSpec);
            PythonContext.PreOperator preExecution = context.preExecution();
            preExecution.operate(script, resultSpec, argumentSpec);
            PythonResultMap resultMap = this.pythonExecutor.execute(script, resultSpec);
            PythonContext.SuccessHandler successHandler = context.successHandler();
            return successHandler.onSuccess(resultMap);
        } catch (Exception e) {
            log.warn("Python script [name: {}] processing failed, applying failure handler", name, e);
            PythonContext.FailureHandler failureHandler = context.failureHandler();
            return failureHandler.onFail(e);
        }
    }
}
