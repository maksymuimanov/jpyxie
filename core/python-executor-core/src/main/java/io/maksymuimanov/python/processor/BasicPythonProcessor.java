package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultContainer;
import io.maksymuimanov.python.executor.PythonResultSpec;
import io.maksymuimanov.python.file.PythonFileReader;
import io.maksymuimanov.python.resolver.PythonArgumentSpec;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicPythonProcessor implements PythonProcessor {
    private final PythonFileReader pythonFileReader;
    private final PythonExecutor pythonExecutor;
    private final PythonResolverHolder pythonResolverHolder;

    @Override
    public PythonResultMap process(PythonContext context) {
        try {
            PythonScript script = context.script();
            PythonResultSpec resultSpec = context.resultSpec();
            PythonArgumentSpec argumentSpec = context.argumentSpec();
            if (script.isFile()) pythonFileReader.readScript(script);
            PythonContext.PreOperator preResolution = context.preResolution();
            preResolution.operate(script, resultSpec, argumentSpec);
            pythonResolverHolder.resolveAll(script, argumentSpec);
            PythonContext.PreOperator preExecution = context.preExecution();
            preExecution.operate(script, resultSpec, argumentSpec);
            PythonResultContainer resultMap = pythonExecutor.execute(script, resultSpec);
            PythonContext.SuccessHandler successHandler = context.successHandler();
            return successHandler.onSuccess(resultMap);
        } catch (Exception e) {
            PythonContext.FailureHandler failureHandler = context.failureHandler();
            return failureHandler.onFail(e);
        }
    }
}
