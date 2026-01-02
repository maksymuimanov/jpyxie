package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.error.ProcessErrorHandler;
import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.finisher.ProcessFinisher;
import io.maksymuimanov.python.output.ProcessOutputHandler;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;
import io.maksymuimanov.python.starter.ProcessStarter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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
public class ProcessPythonExecutor extends AbstractPythonExecutor<String> {
    private static final String WARN_MESSAGE = "Result is null! Consider to print the needed field with '$' identifier!";
    private final ProcessStarter processStarter;
    private final ProcessOutputHandler processOutputHandler;
    private final ProcessErrorHandler processErrorHandler;
    private final ProcessFinisher processFinisher;

    public ProcessPythonExecutor(PythonDeserializer<String> pythonDeserializer,
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
            Map<String, String> resultJsons = this.processOutputHandler.handle(process, resultSpec);
            this.processFinisher.finish(process);
            PythonResultMap resultMap = createResultMap(resultSpec, resultJsons);
            this.warnOnEmptyResult(resultMap);
            return resultMap;
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    private PythonResultMap createResultMap(PythonResultSpec resultSpec, Map<String, String> resultJsons) {
        PythonResultMap resultMap = PythonResultMap.create();
        resultJsons.forEach((key, value) -> deserializeJsonPythonResult(resultSpec, key, value, resultMap));
        return resultMap;
    }

    private void deserializeJsonPythonResult(PythonResultSpec resultSpec, String key, String value, PythonResultMap resultMap) {
        PythonDeserializer<String> jsonPythonDeserializer = this.getPythonDeserializer();
        Object deserialized = jsonPythonDeserializer.deserialize(value, resultSpec.getRequirement(key));
        resultMap.putObject(key, deserialized);
    }

    private void warnOnEmptyResult(PythonResultMap resultMap) {
        if (resultMap.isEmpty()) {
            log.warn(WARN_MESSAGE);
        }
    }
}
