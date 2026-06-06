package io.jpyxie.demo;

import io.jpyxie.python.PythonConstants;
import io.jpyxie.python.executor.PythonResultSpec;
import io.jpyxie.python.processor.PythonContext;
import io.jpyxie.python.processor.PythonProcessor;
import io.jpyxie.python.resolver.PythonArgumentSpec;
import io.jpyxie.python.script.PythonScript;
import lombok.SneakyThrows;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Component
public class PythonTestApplicationRunner implements ApplicationRunner {
    private static final String[] NAMES = {
            "l1/fibonacci_calculation",
            "l1/factorial_and_vowel_count",
            "l1/prime_number_scan",
            "l1/numeric_series_statistics",
            "l1/text_word_length_analysis",

            "l2/numeric_series_aggregation",
            "l2/matrix_multiplication_analysis",
            "l2/character_frequency_ranking",
            "l2/pseudo_random_statistics",
            "l2/floating_point_variance_simulation",

            "l3/trigonometric_matrix_correlation",
            "l3/vector_distance_analysis",
            "l3/iterative_simulation_engine",
            "l3/geometric_path_length_computation",
            "l3/numerical_integration_solver"
    };
    private final Map<String, Long> timeMap;
    private final PythonProcessor pythonProcessor;

    public PythonTestApplicationRunner(PythonProcessor pythonProcessor) {
        this.timeMap = new ConcurrentHashMap<>();
        this.pythonProcessor = pythonProcessor;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            String name = NAMES[Math.abs(i % NAMES.length)];
            PythonScript pythonScript = PythonScript.asFile(name + "_" + i, name + PythonConstants.FILE_FORMAT);
            PythonContext pythonContext = PythonContext.builder(pythonScript)
                    .argumentSpec(PythonArgumentSpec.of()
                            .with("integer", i)
                            .with("float", Math.random())
                            .with("text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                            .with("boolean", Math.random() > 0.5))
                    .resultSpec(PythonResultSpec.of()
                            .require("result", String.class))
                    .build();
            CompletableFuture<Void> taskFuture = CompletableFuture.runAsync(() -> record(pythonContext), ForkJoinPool.commonPool());
            futures.add(taskFuture);
        }

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).get();
        printTime();
    }

    public void printTime() {
        System.out.println("\n==================== PYTHON SCRIPT PERFORMANCE ====================");


        timeMap.entrySet()
                .stream()
                .collect(Collectors.groupingBy(e ->
                                e.getKey().substring(0, e.getKey().lastIndexOf("_")),
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String name = entry.getKey();
                    List<Long> times = entry.getValue();

                    long count = times.size();
                    long min = times.stream().mapToLong(Long::longValue).min().orElse(0);
                    long max = times.stream().mapToLong(Long::longValue).max().orElse(0);
                    double avg = times.stream().mapToLong(Long::longValue).average().orElse(0);

                    System.out.printf(
                            "%-45s | runs=%2d | avg=%7.2f ms | min=%5d ms | max=%5d ms%n",
                            name, count, avg, min, max
                    );
                });

        System.out.println("\n-------------------------------------------------------------\n");


        timeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String name = entry.getKey();
                    Long time = entry.getValue();

                    System.out.printf(
                            "%-45s | time=%5d ms%n",
                            name, time
                    );
                });

        System.out.println("=============================================================");
    }

    @SneakyThrows
    public void record(PythonContext context) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        pythonProcessor.process(context);
        stopWatch.stop();
        String name = context.script().name();
        timeMap.put(name, stopWatch.getTotalTimeMillis());
    }
}
