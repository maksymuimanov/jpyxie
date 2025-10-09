package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.aspect.AsyncPythonAnnotationEvaluator;
import io.maksymuimanov.python.aspect.BasicPythonAnnotationEvaluator;
import io.maksymuimanov.python.aspect.PythonAfterAspect;
import io.maksymuimanov.python.aspect.PythonBeforeAspect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Python aspect execution.
 *
 * <p>Defines when asynchronous execution should be applied for Python script calls
 * intercepted by Spring AOP aspects.</p>
 *
 * <p>Properties are bound from the application configuration using the prefix
 * {@code spring.python.aspect}.</p>
 *
 * <p><b>AsyncScopes values:</b></p>
 * <ul>
 *   <li>{@link AsyncProperties.Scope#BEFORE} — execute Python scripts asynchronously before the target method is invoked.</li>
 *   <li>{@link AsyncProperties.Scope#AFTER} — execute Python scripts asynchronously after the target method has completed.</li>
 * </ul>
 *
 * <p><b>Example (application.yml):</b></p>
 * <pre>{@code
 * spring:
 *   python:
 *     aspect:
 *       async:
 *         enabled: true
 *         scopes: before, after
 *         core-pool-size: 10
 *         max-pool-size: 50
 *         queue-capacity: 100
 *         thread-name-prefix: AsyncPythonAspect-
 *         rejection-policy: caller_runs
 * }</pre>
 *
 * @see PythonBeforeAspect
 * @see PythonAfterAspect
 * @see BasicPythonAnnotationEvaluator
 * @see AsyncPythonAnnotationEvaluator
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.aspect")
public class PythonAspectProperties {
    private AsyncProperties async = new AsyncProperties();

    /**
     * Properties for asynchronous execution configuration within a Python aspect.
     *
     * <p>Includes the scopes when async execution applies and thread pool settings
     * for the async executor.</p>
     */
    @Getter @Setter
    public static class AsyncProperties {
        /**
         * Whether asynchronous aspect execution is enabled.
         */
        private boolean enabled = false;
        /**
         * Defines when asynchronous execution should be applied (before or after method execution).
         */
        private Scope[] scopes = new Scope[]{Scope.BEFORE, Scope.AFTER};
        /**
         * Core number of threads to keep in the async thread pool.
         */
        private int corePoolSize = 10;
        /**
         * Maximum number of threads allowed in the async thread pool.
         */
        private int maxPoolSize = 50;
        /**
         * Capacity of the task queue used by the async executor.
         */
        private int queueCapacity = 100;
        /**
         * Prefix to use for async executor thread names.
         */
        private String threadNamePrefix = "AsyncPythonAspect-";
        /**
         * Policy used when the async executor queue is full and a new task cannot be accepted.
         */
        private RejectionPolicy rejectionPolicy = RejectionPolicy.CALLER_RUNS;

        /**
         * Scopes defining when asynchronous execution of Python scripts is applied.
         */
        public enum Scope {
            BEFORE, AFTER
        }

        /**
         * Rejection policy defining what asynchronous should do on pool starvation.
         */
        public enum RejectionPolicy {
            CALLER_RUNS, ABORT, DISCARD, DISCARD_OLDEST
        }
    }
}
