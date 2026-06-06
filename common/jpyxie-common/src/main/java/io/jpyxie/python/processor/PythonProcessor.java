package io.jpyxie.python.processor;

import io.jpyxie.python.executor.PythonResultSpec;
import io.jpyxie.python.resolver.PythonArgumentSpec;
import io.jpyxie.python.PythonRepresentation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public interface PythonProcessor {
    default CompletableFuture<PythonResultMap> processAsync(PythonRepresentation script) {
        return CompletableFuture.supplyAsync(() -> this.process(script), ForkJoinPool.commonPool());
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonRepresentation script, PythonArgumentSpec argumentSpec) {
        return CompletableFuture.supplyAsync(() -> this.process(script, argumentSpec), ForkJoinPool.commonPool());
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonRepresentation script, PythonResultSpec resultSpec) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultSpec), ForkJoinPool.commonPool());
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonRepresentation script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultSpec, argumentSpec), ForkJoinPool.commonPool());
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonContext context) {
        return CompletableFuture.supplyAsync(() -> this.process(context), ForkJoinPool.commonPool());
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonRepresentation script, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script), executor);
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonRepresentation script, PythonArgumentSpec argumentSpec, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, argumentSpec), executor);
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonRepresentation script, PythonResultSpec resultSpec, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultSpec), executor);
    }


    default CompletableFuture<PythonResultMap> processAsync(PythonRepresentation script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(script, resultSpec, argumentSpec), executor);
    }

    default CompletableFuture<PythonResultMap> processAsync(PythonContext context, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.process(context), executor);
    }

    default PythonResultMap process(PythonRepresentation script) {
        PythonResultSpec resultSpec = PythonResultSpec.empty();
        return this.process(script, resultSpec);
    }

    default PythonResultMap process(PythonRepresentation script, PythonArgumentSpec argumentSpec) {
        PythonResultSpec resultSpec = PythonResultSpec.empty();
        return this.process(script, resultSpec, argumentSpec);
    }

    default PythonResultMap process(PythonRepresentation script, PythonResultSpec resultSpec) {
        PythonArgumentSpec argumentSpec = PythonArgumentSpec.empty();
        return this.process(script, resultSpec, argumentSpec);
    }

    default PythonResultMap process(PythonRepresentation script, PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec) {
        PythonContext context = PythonContext.builder()
                .script(script)
                .resultSpec(resultSpec)
                .argumentSpec(argumentSpec)
                .build();
        return this.process(context);
    }

    PythonResultMap process(PythonContext context);
}