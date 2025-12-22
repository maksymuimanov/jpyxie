package io.maksymuimanov.python.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.finisher.ProcessFinisher;
import io.maksymuimanov.python.input.ProcessErrorHandler;
import io.maksymuimanov.python.input.ProcessInputHandler;
import io.maksymuimanov.python.input.ResultHolder;
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
 *   <li>Handling the process's input stream to capture the Python script output via {@link ProcessInputHandler}.</li>
 *   <li>Handling the process's error stream to capture error messages via {@link ProcessErrorHandler}.</li>
 *   <li>Ensuring the process completes correctly using {@link ProcessFinisher}.</li>
 *   <li>Converting the captured JSON output into the specified Java type from {@link ResultHolder}.</li>
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
 * @see ProcessInputHandler
 * @see ProcessErrorHandler
 * @see ProcessFinisher
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
public class ProcessPythonExecutor extends AbstractPythonExecutor<ResultHolder<String>> {
    private final ProcessStarter processStarter;
    private final ProcessInputHandler inputProcessHandler;
    private final ProcessErrorHandler errorProcessHandler;
    private final ResultHolder<String> resultHolder;
    private final ObjectMapper objectMapper;
    private final ProcessFinisher processFinisher;

    protected ProcessPythonExecutor(PythonResultFieldNameProvider resultFieldProvider,
                                    ProcessStarter processStarter,
                                    ProcessInputHandler inputProcessHandler,
                                    ProcessErrorHandler errorProcessHandler,
                                    ResultHolder<String> resultHolder,
                                    ObjectMapper objectMapper,
                                    ProcessFinisher processFinisher) {
        super(resultFieldProvider);
        this.processStarter = processStarter;
        this.inputProcessHandler = inputProcessHandler;
        this.errorProcessHandler = errorProcessHandler;
        this.resultHolder = resultHolder;
        this.objectMapper = objectMapper;
        this.processFinisher = processFinisher;
    }

    @Override
    public @Nullable <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription) {
        try {
            Process process = processStarter.start(script);
            errorProcessHandler.handle(process);
            inputProcessHandler.handle(process, resultDescription);
            processFinisher.finish(process);
            return this.getResult(resultDescription, resultHolder);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Override
    public Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        try {
            Process process = processStarter.start(script);
            errorProcessHandler.handle(process);
            inputProcessHandler.handle(process, resultDescriptions);
            processFinisher.finish(process);
            return this.getResultMap(resultDescriptions, resultHolder);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Override
    protected @Nullable <R> R getResult(PythonResultDescription<R> resultDescription, ResultHolder<String> resultContainer) {
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
