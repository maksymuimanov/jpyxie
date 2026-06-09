package io.jpyxie.python.executor;

import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessPythonExecutor implements PythonExecutor {
    private final ProcessStarter processStarter;
    private final ProcessOutputHandler processOutputHandler;
    private final ProcessErrorHandler processErrorHandler;
    private final ProcessFinisher processFinisher;

    public ProcessPythonExecutor(ProcessStarter processStarter,
                                 ProcessOutputHandler processOutputHandler,
                                 ProcessErrorHandler processErrorHandler,
                                 ProcessFinisher processFinisher) {
        this.processStarter = processStarter;
        this.processOutputHandler = processOutputHandler;
        this.processErrorHandler = processErrorHandler;
        this.processFinisher = processFinisher;
    }

    @Override
    public PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec) {
        try {
            Process process = this.processStarter.start(script);
            this.processErrorHandler.handle(process);
            this.processOutputHandler.handle(process);
            this.processFinisher.finish(process);
            this.ensureResultIsEmpty(resultSpec);
            return PythonResultMap.empty();
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    private void ensureResultIsEmpty(PythonResultSpec resultSpec) {
        if (!resultSpec.isEmpty()) {
            PythonExecutionException exception = new PythonExecutionException("Result is null! ProcessPythonExecutor is fire-and-forget executor!");
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }
}
