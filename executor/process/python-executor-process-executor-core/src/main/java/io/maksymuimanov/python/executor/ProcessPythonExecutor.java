package io.maksymuimanov.python.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.finisher.ProcessFinisher;
import io.maksymuimanov.python.output.ProcessErrorHandler;
import io.maksymuimanov.python.output.ProcessOutputHandler;
import io.maksymuimanov.python.script.PythonScript;
import io.maksymuimanov.python.starter.ProcessStarter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

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
public class ProcessPythonExecutor extends AbstractPythonExecutor<ProcessOutputHandler> {
    private final ProcessStarter processStarter;
    private final ProcessOutputHandler processOutputHandler;
    private final ProcessErrorHandler processErrorHandler;
    private final ObjectMapper objectMapper;
    private final ProcessFinisher processFinisher;

    public ProcessPythonExecutor(ProcessStarter processStarter,
                                 ProcessOutputHandler processOutputHandler,
                                 ProcessErrorHandler processErrorHandler,
                                 ObjectMapper objectMapper,
                                 ProcessFinisher processFinisher) {
        this.processStarter = processStarter;
        this.processOutputHandler = processOutputHandler;
        this.processErrorHandler = processErrorHandler;
        this.objectMapper = objectMapper;
        this.processFinisher = processFinisher;
    }

    @Override
    public @Nullable <R> R execute(PythonScript script, PythonResultSpec<R> resultDescription) {
        try {
            Process process = this.processStarter.start(script);
            this.processErrorHandler.handle(process);
            this.processOutputHandler.handle(process, resultDescription);
            this.processFinisher.finish(process);
            return this.getResult(resultDescription, this.processOutputHandler);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Override
    public Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultSpec<?>> resultDescriptions) {
        try {
            Process process = this.processStarter.start(script);
            this.processErrorHandler.handle(process);
            this.processOutputHandler.handle(process, resultDescriptions);
            this.processFinisher.finish(process);
            return this.getResultMap(resultDescriptions, this.processOutputHandler);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Override
    protected @Nullable <R> R getResult(PythonResultSpec<R> resultDescription, ProcessOutputHandler resultContainer) {
        R value = resultDescription.getValue((type, fieldName) -> {
            String resultJson = resultContainer.getResult(fieldName);
            return this.parseJson(resultJson, type);
        });
        if (value == null) {
            log.warn("Result is null! Maybe you should try to set the spring.python.resolver.result.is-printed=true");
        }
        return value;
    }

    private <R> R parseJson(String json, Class<R> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new PythonExecutionException(e);
        }
    }
}
