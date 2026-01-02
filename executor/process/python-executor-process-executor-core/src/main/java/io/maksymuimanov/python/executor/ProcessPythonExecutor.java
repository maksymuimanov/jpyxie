package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the {@link PythonExecutor} interface that executes Python scripts locally via Process API.
 * <p>
 * This class manages the lifecycle of a local Python process by:
 * <ul>
 *   <li>Starting the Python process with the provided script using {@link ProcessStarter}.</li>
 *   <li>Handling the process's input stream to capture the Python script output via {@link ProcessOutputHandler}.</li>
 *   <li>Handling the process's error stream to capture error messages via {@link ProcessErrorHandler}.</li>
 *   <li>Ensuring the process completes correctly using {@link ProcessFinisher}.</li>
 *   <li>Converting the captured JSON output into the specified Java type from {@link ProcessOutputHandler}.</li>
 * </ul>
 * <p>
 * Usage example:
 * <pre>{@code
 * PythonExecutor executor = new LocalPythonExecutor(processStarter, inputHandler, errorHandler, objectMapper, processFinisher);
 * String script = "print('Hello from Python')";
 * String body = executor.execute(script, String.class);
 * }</pre>
 *
 * @see PythonExecutor
 * @see ProcessStarter
 * @see ProcessOutputHandler
 * @see ProcessErrorHandler
 * @see ProcessFinisher
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
public class ProcessPythonExecutor extends AbstractPythonExecutor<ProcessPythonResponse> {
    private static final String EMPTY_RESULT_ERROR_MESSAGE = "Result is null! Consider to print the needed field with '$' identifier!";
    private final ProcessStarter processStarter;
    private final ProcessOutputHandler processOutputHandler;
    private final ProcessErrorHandler processErrorHandler;
    private final ProcessFinisher processFinisher;

    public ProcessPythonExecutor(PythonDeserializer<ProcessPythonResponse> pythonDeserializer,
                                 ProcessStarter processStarter,
                                 ProcessOutputHandler processOutputHandler,
                                 ProcessErrorHandler processErrorHandler,
                                 ProcessFinisher processFinisher) {
        super(pythonDeserializer);
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
            ProcessPythonResponse processResponse = this.processOutputHandler.handle(process, resultSpec);
            this.processFinisher.finish(process);
            PythonResultMap resultMap = this.createResultMap(resultSpec, processResponse);
            this.validateResult(resultSpec, resultMap);
            return resultMap;
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    private void validateResult(PythonResultSpec resultSpec, PythonResultMap resultMap) {
        if (!resultSpec.isEmpty() && resultMap.isEmpty()) {
            log.error(EMPTY_RESULT_ERROR_MESSAGE);
            throw new PythonExecutionException(EMPTY_RESULT_ERROR_MESSAGE);
        }
    }
}
