package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultDescription;
import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;

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

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(String script, Class<R> resultClass) {
        return this.processAsync(script, resultClass, Map.of());
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(String script, PythonResultDescription<R> resultDescription) {
        return this.processAsync(script, resultDescription, Map.of());
    }

    default CompletableFuture<List<PythonExecutionResponse<?>>> processAsync(String script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        return this.processAsync(script, resultDescriptions, Map.of());
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(PythonScript script, Class<R> resultClass) {
        return this.processAsync(script, resultClass, Map.of());
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(PythonScript script, PythonResultDescription<R> resultDescription) {
        return this.processAsync(script, resultDescription, Map.of());
    }

    default CompletableFuture<List<PythonExecutionResponse<?>>> processAsync(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        return this.processAsync(script, resultDescriptions, Map.of());
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(String script, Class<R> resultClass, Map<String, Object> arguments) {
        return this.processAsync(script, resultClass, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(String script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments) {
        return this.processAsync(script, resultDescription, arguments, ForkJoinPool.commonPool());
    }

    default CompletableFuture<List<PythonExecutionResponse<?>>> processAsync(String script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments) {
        return this.processAsync(script, resultDescriptions, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(PythonScript script, Class<R> resultClass, Map<String, Object> arguments) {
        return this.processAsync(script, resultClass, arguments, ForkJoinPool.commonPool());
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(PythonScript script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments) {
        return this.processAsync(script, resultDescription, arguments, ForkJoinPool.commonPool());
    }

    default CompletableFuture<List<PythonExecutionResponse<?>>> processAsync(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments) {
        return this.processAsync(script, resultDescriptions, arguments, ForkJoinPool.commonPool());
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

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(String script, Class<R> resultClass, Executor executor) {
        return this.processAsync(script, resultClass, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(String script, PythonResultDescription<R> resultDescription, Executor executor) {
        return this.processAsync(script, resultDescription, Map.of(), executor);
    }

    default CompletableFuture<List<PythonExecutionResponse<?>>> processAsync(String script, Iterable<PythonResultDescription<?>> resultDescriptions, Executor executor) {
        return this.processAsync(script, resultDescriptions, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(PythonScript script, Class<R> resultClass, Executor executor) {
        return this.processAsync(script, resultClass, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(PythonScript script, PythonResultDescription<R> resultDescription, Executor executor) {
        return this.processAsync(script, resultDescription, Map.of(), executor);
    }

    default CompletableFuture<List<PythonExecutionResponse<?>>> processAsync(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Executor executor) {
        return this.processAsync(script, resultDescriptions, Map.of(), executor);
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(String script, Class<R> resultClass, Map<String, Object> arguments, Executor executor) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAsync(pythonScript, resultClass, arguments, executor);
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(String script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments, Executor executor) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAsync(pythonScript, resultDescription, arguments, executor);
    }

    default CompletableFuture<List<PythonExecutionResponse<?>>> processAsync(String script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments, Executor executor) {
        PythonScript pythonScript = new PythonScript(script);
        return this.processAsync(pythonScript, resultDescriptions, arguments, executor);
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(PythonScript script, Class<R> resultClass, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultClass, arguments), executor);
    }

    default <R> CompletableFuture<PythonExecutionResponse<R>> processAsync(PythonScript script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultDescription, arguments), executor);
    }

    default CompletableFuture<List<PythonExecutionResponse<?>>> processAsync(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultDescriptions, arguments), executor);
    }

    /**
     * Processes and executes a Python script without additional arguments or body mapping.
     *
     * @param script non-{@code null} Python script to execute
     */
    default PythonExecutionResponse<Void> process(String script) {
        return this.process(script, Void.class, Map.of());
    }

    /**
     * Processes and executes a Python script without additional arguments or body mapping.
     *
     * @param script non-{@code null} Python script to execute
     */
    default PythonExecutionResponse<Void> process(PythonScript script) {
        return this.process(script, Void.class, Map.of());
    }

    /**
     * Processes and executes a Python script with the given argument map.
     *
     * @param script non-{@code null} Python script to execute
     * @param arguments a map of arguments accessible to resolvers during preprocessing
     */
    default PythonExecutionResponse<Void> process(String script, Map<String, Object> arguments) {
        return this.process(script, Void.class, arguments);
    }

    /**
     * Processes and executes a Python script with the given argument map.
     *
     * @param script non-{@code null} Python script to execute
     * @param arguments a map of arguments accessible to resolvers during preprocessing
     */
    default PythonExecutionResponse<Void> process(PythonScript script, Map<String, Object> arguments) {
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
    default <R> PythonExecutionResponse<R> process(String script, Class<R> resultClass) {
        return this.process(script, resultClass, Map.of());
    }

    default <R> PythonExecutionResponse<R> process(String script, PythonResultDescription<R> resultDescription) {
        return this.process(script, resultDescription, Map.of());
    }

    default List<PythonExecutionResponse<?>> process(String script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        return this.process(script, resultDescriptions, Map.of());
    }

    /**
     * Processes and executes a Python script, mapping the body to the specified type.
     *
     * @param <R> the type of body expected from script execution
     * @param script non-{@code null} Python script to execute
     * @param resultClass the class representing the expected body type (nullable)
     * @return the body of execution cast to {@code R}, or {@code null} if the script returns nothing
     */
    default <R> PythonExecutionResponse<R> process(PythonScript script, Class<R> resultClass) {
        return this.process(script, resultClass, Map.of());
    }

    default <R> PythonExecutionResponse<R> process(PythonScript script, PythonResultDescription<R> resultDescription) {
        return this.process(script, resultDescription, Map.of());
    }

    default List<PythonExecutionResponse<?>> process(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        return this.process(script, resultDescriptions, Map.of());
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
    default <R> PythonExecutionResponse<R> process(String script, Class<R> resultClass, Map<String, Object> arguments) {
        PythonScript pythonScript = new PythonScript(script);
        return this.process(pythonScript, resultClass, arguments);
    }

    default <R> PythonExecutionResponse<R> process(String script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments) {
        PythonScript pythonScript = new PythonScript(script);
        return this.process(pythonScript, resultDescription, arguments);
    }

    default List<PythonExecutionResponse<?>> process(String script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments) {
        PythonScript pythonScript = new PythonScript(script);
        return this.process(pythonScript, resultDescriptions, arguments);
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
    <R> PythonExecutionResponse<R> process(PythonScript script, Class<R> resultClass, Map<String, Object> arguments);
    
    <R> PythonExecutionResponse<R> process(PythonScript script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments);

    List<PythonExecutionResponse<?>> process(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments);
}