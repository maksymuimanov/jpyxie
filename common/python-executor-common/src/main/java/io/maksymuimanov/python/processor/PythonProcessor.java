package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.executor.PythonResultSpec;
import io.maksymuimanov.python.resolver.PythonArgumentSpec;
import io.maksymuimanov.python.script.PythonScript;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public interface PythonProcessor {
    default CompletableFuture<Void> processAsync(PythonScript script) {
        return CompletableFuture.runAsync(() -> this.process(script), ForkJoinPool.commonPool());
    }

    default CompletableFuture<Void> processAsync(PythonScript script, PythonArgumentSpec argumentSpec) {
        return CompletableFuture.runAsync(() -> this.process(script, argumentSpec), ForkJoinPool.commonPool());
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonScript script, PythonResultSpec resultSpec) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultSpec), ForkJoinPool.commonPool());
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonScript script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultSpec, argumentSpec), ForkJoinPool.commonPool());
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonContext context) {
        return CompletableFuture.supplyAsync(() -> this.process(context), ForkJoinPool.commonPool());
    }

    default CompletableFuture<Void> processAsync(PythonScript script, Executor executor) {
        return CompletableFuture.runAsync(() -> this.process(script), executor);
    }

    default CompletableFuture<Void> processAsync(PythonScript script, PythonArgumentSpec argumentSpec, Executor executor) {
        return CompletableFuture.runAsync(() -> this.process(script, argumentSpec), executor);
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonScript script, PythonResultSpec resultSpec, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultSpec), executor);
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonScript script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultSpec, argumentSpec), executor);
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonContext context, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(context), executor);
    }

    default void process(PythonScript script) {
        PythonResultSpec resultSpec = PythonResultSpec.empty();
        this.process(script, resultSpec);
    }

    default void process(PythonScript script, PythonArgumentSpec argumentSpec) {
        PythonResultSpec resultSpec = PythonResultSpec.empty();
        this.process(script, resultSpec, argumentSpec);
    }

    default PythonResultMap process(PythonScript script, PythonResultSpec resultSpec) {
        PythonArgumentSpec argumentSpec = PythonArgumentSpec.empty();
        return this.process(script, resultSpec, argumentSpec);
    }

    default PythonResultMap process(PythonScript script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec) {
        PythonContext context = PythonContext.builder(script)
                .resultSpec(resultSpec)
                .argumentSpec(argumentSpec)
                .build();
        return this.process(context);
    }

    PythonResultMap process(PythonContext context);
}