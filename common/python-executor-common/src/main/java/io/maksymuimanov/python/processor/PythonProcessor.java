package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultDescription;
import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.response.PythonResponse;
import io.maksymuimanov.python.script.PythonScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * Defines the contract for processing and executing Python scripts, acting as a bridge between
 * {@link PythonExecutor} and {@link PythonResolverHolder}.
 *
 * <p>Implementations of this interface are responsible for:</p>
 * <ul>
 *     <li>Pre-processing Python scripts before execution</li>
 *     <li>Applying transformations and expression resolution using registered resolvers using {@link PythonResolver} instances</li>
 *     <li>Delegating the execution to a {@link PythonExecutor}</li>
 * </ul>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * PythonProcessor processor = ...;
 * String script = "print('Hello World ')";
 * processor.process(script);
 * }</pre>
 * @see PythonExecutor
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface PythonProcessor {
    default CompletableFuture<Void> processAsync(String script) {
        return this.processAsync(script, Map.of());
    }

    default CompletableFuture<Void> processAsync(PythonScript script) {
        return this.processAsync(script, Map.of());
    }

    default CompletableFuture<Void> processAsync(String script, Map<String, Object> arguments) {
        return this.processAsync(script, arguments, ForkJoinPool.commonPool());
    }

    default CompletableFuture<Void> processAsync(PythonScript script, Map<String, Object> arguments) {
        return this.processAsync(script, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, Class<R> resultClass) {
        return this.processAsync(script, resultClass, Map.of());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, String resultName, Class<R> resultClass) {
        return this.processAsync(script, resultName, resultClass, Map.of());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, PythonResultDescription<R> resultDescription) {
        return this.processAsync(script, resultDescription, Map.of());
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(String script, Map<String, Class<?>> resultDescriptionMap) {
        return this.processAllAsync(script, resultDescriptionMap, Map.of());
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(String script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        return this.processAllAsync(script, resultDescriptions, Map.of());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, Class<R> resultClass) {
        return this.processAsync(script, resultClass, Map.of());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, PythonResultDescription<R> resultDescription) {
        return this.processAsync(script, resultDescription, Map.of());
    }


    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, String resultName, Class<R> resultClass) {
        return this.processAsync(script, resultName, resultClass, Map.of());
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(PythonScript script, Map<String, Class<?>> resultDescriptionMap) {
        return this.processAllAsync(script, resultDescriptionMap, Map.of());
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        return this.processAllAsync(script, resultDescriptions, Map.of());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, Class<R> resultClass, Map<String, Object> arguments) {
        return this.processAsync(script, resultClass, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, String resultName, Class<R> resultClass, Map<String, Object> arguments) {
        return this.processAsync(script, resultName, resultClass, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments) {
        return this.processAsync(script, resultDescription, arguments, ForkJoinPool.commonPool());
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(String script, Map<String, Class<?>> resultDescriptionMap, Map<String, Object> arguments) {
        return this.processAllAsync(script, resultDescriptionMap, arguments, ForkJoinPool.commonPool());
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(String script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments) {
        return this.processAllAsync(script, resultDescriptions, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, Class<R> resultClass, Map<String, Object> arguments) {
        return this.processAsync(script, resultClass, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, String resultName, Class<R> resultClass, Map<String, Object> arguments) {
        return this.processAsync(script, resultName, resultClass, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments) {
        return this.processAsync(script, resultDescription, arguments, ForkJoinPool.commonPool());
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(PythonScript script, Map<String, Class<?>> resultDescriptionMap, Map<String, Object> arguments) {
        return this.processAllAsync(script, resultDescriptionMap, arguments, ForkJoinPool.commonPool());
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments) {
        return this.processAllAsync(script, resultDescriptions, arguments, ForkJoinPool.commonPool());
    }

    default CompletableFuture<Void> processAsync(String script, Executor executor) {
        return this.processAsync(script, Map.of(), executor);
    }

    default CompletableFuture<Void> processAsync(PythonScript script, Executor executor) {
        return this.processAsync(script, Map.of(), executor);
    }

    default CompletableFuture<Void> processAsync(String script, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.runAsync(() -> this.process(script, arguments), executor);
    }

    default CompletableFuture<Void> processAsync(PythonScript script, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.runAsync(() -> this.process(script, arguments), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, Class<R> resultClass, Executor executor) {
        return this.processAsync(script, resultClass, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, String resultName, Class<R> resultClass, Executor executor) {
        return this.processAsync(script, resultName, resultClass, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, PythonResultDescription<R> resultDescription, Executor executor) {
        return this.processAsync(script, resultDescription, Map.of(), executor);
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(String script, Map<String, Class<?>> resultDescriptionMap, Executor executor) {
        return this.processAllAsync(script, resultDescriptionMap, Map.of(), executor);
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(String script, Iterable<PythonResultDescription<?>> resultDescriptions, Executor executor) {
        return this.processAllAsync(script, resultDescriptions, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, Class<R> resultClass, Executor executor) {
        return this.processAsync(script, resultClass, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, String resultName, Class<R> resultClass, Executor executor) {
        return this.processAsync(script, resultName, resultClass, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, PythonResultDescription<R> resultDescription, Executor executor) {
        return this.processAsync(script, resultDescription, Map.of(), executor);
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(PythonScript script, Map<String, Class<?>> resultDescriptionMap, Executor executor) {
        return this.processAllAsync(script, resultDescriptionMap, Map.of(), executor);
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Executor executor) {
        return this.processAllAsync(script, resultDescriptions, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, Class<R> resultClass, Map<String, Object> arguments, Executor executor) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAsync(pythonScript, resultClass, arguments, executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, String resultName, Class<R> resultClass, Map<String, Object> arguments, Executor executor) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAsync(pythonScript, resultName, resultClass, arguments, executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(String script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments, Executor executor) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAsync(pythonScript, resultDescription, arguments, executor);
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(String script, Map<String, Class<?>> resultDescriptionMap, Map<String, Object> arguments, Executor executor) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAllAsync(pythonScript, resultDescriptionMap, arguments, executor);
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(String script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments, Executor executor) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAllAsync(pythonScript, resultDescriptions, arguments, executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, Class<R> resultClass, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultClass, arguments), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, String resultName, Class<R> resultClass, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultName, resultClass, arguments), executor);
    }

    default <R> CompletableFuture<PythonResponse<R>> processAsync(PythonScript script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultDescription, arguments), executor);
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(PythonScript script, Map<String, Class<?>> resultDescriptionMap, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.processAll(script, resultDescriptionMap, arguments), executor);
    }

    default CompletableFuture<List<PythonResponse<?>>> processAllAsync(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.processAll(script, resultDescriptions, arguments), executor);
    }

    /**
     * Processes and executes a Python script without additional arguments or body mapping.
     *
     * @param script non-{@code null} Python script to execute
     */
    default PythonResponse<Void> process(String script) {
        return this.process(script, Void.class, Map.of());
    }

    /**
     * Processes and executes a Python script without additional arguments or body mapping.
     *
     * @param script non-{@code null} Python script to execute
     */
    default PythonResponse<Void> process(PythonScript script) {
        return this.process(script, Void.class, Map.of());
    }

    /**
     * Processes and executes a Python script with the given argument map.
     *
     * @param script non-{@code null} Python script to execute
     * @param arguments a map of arguments accessible to resolvers during preprocessing
     */
    default PythonResponse<Void> process(String script, Map<String, Object> arguments) {
        return this.process(script, Void.class, arguments);
    }

    /**
     * Processes and executes a Python script with the given argument map.
     *
     * @param script non-{@code null} Python script to execute
     * @param arguments a map of arguments accessible to resolvers during preprocessing
     */
    default PythonResponse<Void> process(PythonScript script, Map<String, Object> arguments) {
        return this.process(script, Void.class, arguments);
    }

    /**
     * Processes and executes a Python script, mapping the body to the specified type.
     *
     * @param <R> the type of body expected from script execution
     * @param script non-{@code null} Python script to execute
     * @param resultClass the class representing the expected body type (nullable)
     * @return the body of execution cast to {@code R}, or {@code null} if the script returns nothing
     */
    default <R> PythonResponse<R> process(String script, Class<R> resultClass) {
        return this.process(script, resultClass, Map.of());
    }

    default <R> PythonResponse<R> process(String script, String resultName, Class<R> resultClass) {
        return this.process(script, resultName, resultClass, Map.of());
    }

    default <R> PythonResponse<R> process(String script, PythonResultDescription<R> resultDescription) {
        return this.process(script, resultDescription, Map.of());
    }

    default List<PythonResponse<?>> processAll(String script, Map<String, Class<?>> resultDescriptionMap) {
        return this.processAll(script, resultDescriptionMap, Map.of());
    }

    default List<PythonResponse<?>> processAll(String script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        return this.processAll(script, resultDescriptions, Map.of());
    }

    /**
     * Processes and executes a Python script, mapping the body to the specified type.
     *
     * @param <R> the type of body expected from script execution
     * @param script non-{@code null} Python script to execute
     * @param resultClass the class representing the expected body type (nullable)
     * @return the body of execution cast to {@code R}, or {@code null} if the script returns nothing
     */
    default <R> PythonResponse<R> process(PythonScript script, Class<R> resultClass) {
        return this.process(script, resultClass, Map.of());
    }

    default <R> PythonResponse<R> process(PythonScript script, String resultName, Class<R> resultClass) {
        return this.process(script, resultName, resultClass, Map.of());
    }

    default <R> PythonResponse<R> process(PythonScript script, PythonResultDescription<R> resultDescription) {
        return this.process(script, resultDescription, Map.of());
    }

    default List<PythonResponse<?>> processAll(PythonScript script, Map<String, Class<?>> resultDescriptionMap) {
        return this.processAll(script, resultDescriptionMap, Map.of());
    }

    default List<PythonResponse<?>> processAll(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        return this.processAll(script, resultDescriptions, Map.of());
    }

    /**
     * Processes and executes a Python script with arguments and optional body mapping.
     *
     * @param <R> the type of body expected from script execution
     * @param script non-{@code null} Python script to execute
     * @param resultClass the class representing the expected body type (nullable)
     * @param arguments a map of arguments accessible to resolvers during preprocessing
     * @return the body of execution cast to {@code R}, or {@code null} if the script returns nothing
     */
    default <R> PythonResponse<R> process(String script, Class<R> resultClass, Map<String, Object> arguments) {
        PythonScript pythonScript = new PythonScript(script);
        return this.process(pythonScript, resultClass, arguments);
    }

    default <R> PythonResponse<R> process(String script, String resultName, Class<R> resultClass, Map<String, Object> arguments) {
        PythonScript pythonScript = new PythonScript(script);
        return this.process(pythonScript, resultName, resultClass, arguments);
    }

    default <R> PythonResponse<R> process(String script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments) {
        PythonScript pythonScript = new PythonScript(script);
        return this.process(pythonScript, resultDescription, arguments);
    }

    default List<PythonResponse<?>> processAll(String script, Map<String, Class<?>> resultDescriptionMap, Map<String, Object> arguments) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAll(pythonScript, resultDescriptionMap, arguments);
    }

    default List<PythonResponse<?>> processAll(String script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAll(pythonScript, resultDescriptions, arguments);
    }

    /**
     * Processes and executes a Python script with arguments and optional body mapping.
     *
     * @param <R> the type of body expected from script execution
     * @param script non-{@code null} Python script to execute
     * @param resultClass the class representing the expected body type (nullable)
     * @param arguments a map of arguments accessible to resolvers during preprocessing
     * @return the body of execution cast to {@code R}, or {@code null} if the script returns nothing
     */
    <R> PythonResponse<R> process(PythonScript script, Class<R> resultClass, Map<String, Object> arguments);
    
    default <R> PythonResponse<R> process(PythonScript script, String resultName, Class<R> resultClass, Map<String, Object> arguments) {
        PythonResultDescription<R> resultDescription = new PythonResultDescription<>(resultClass, resultName);
        return this.process(script, resultDescription, arguments);
    }

    <R> PythonResponse<R> process(PythonScript script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments);

    default List<PythonResponse<?>> processAll(PythonScript script, Map<String, Class<?>> resultDescriptionMap, Map<String, Object> arguments) {
        List<PythonResultDescription<?>> resultDescriptions = new ArrayList<>();
        for (Map.Entry<String, Class<?>> entry : resultDescriptionMap.entrySet()) {
            PythonResultDescription<?> resultDescription = new PythonResultDescription<>(entry.getValue(), entry.getKey());
            resultDescriptions.add(resultDescription);
        }
        return this.processAll(script, resultDescriptions, arguments);
    }

    List<PythonResponse<?>> processAll(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments);
}