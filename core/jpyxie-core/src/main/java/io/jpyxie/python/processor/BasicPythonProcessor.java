package io.jpyxie.python.processor;

import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.executor.PythonResultSpec;
import io.jpyxie.python.resolver.PythonArgumentSpec;
import io.jpyxie.python.resolver.PythonResolverHolder;
import io.jpyxie.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BasicPythonProcessor implements PythonProcessor {
    private final PythonExecutor pythonExecutor;
    private final PythonResolverHolder pythonResolverHolder;

    @Override
    public PythonResultMap process(PythonContext context) {
        PythonScript script = context.getScript();
        String name = script.getName();
        try {
            log.debug("Processing Python script [name: {}]", name);
            PythonResultSpec resultSpec = context.getResultSpec();
            PythonArgumentSpec argumentSpec = context.getArgumentSpec();
            PythonContext.PreOperator preResolution = context.getBeforeResolvers();
            preResolution.operate(script, resultSpec, argumentSpec);
            this.pythonResolverHolder.resolveAll(script, argumentSpec);
            PythonContext.PreOperator preExecution = context.getBeforeExecutor();
            preExecution.operate(script, resultSpec, argumentSpec);
            PythonResultMap resultMap = this.pythonExecutor.execute(script, resultSpec);
            PythonContext.SuccessHandler successHandler = context.getOnSuccess();
            return successHandler.handleSuccess(resultMap);
        } catch (Exception e) {
            PythonContext.FailureHandler failureHandler = context.getOnFailure();
            return failureHandler.handleFailure(e);
        }
    }
}
