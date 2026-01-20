package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.annotation.PythonAfter;
import io.maksymuimanov.python.annotation.PythonAfters;
import io.maksymuimanov.python.annotation.PythonBefore;
import io.maksymuimanov.python.annotation.PythonBefores;
import io.maksymuimanov.python.aspect.*;
import io.maksymuimanov.python.processor.PythonProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Spring Boot autoconfiguration for registering beans required to process
 * Python script annotations in aspects.
 *
 * <p>This configuration defines default implementations for core components such as:
 * <ul>
 *   <li>{@link ProfileChecker} - to validate active Spring profiles before Python script execution.</li>
 *   <li>{@link PythonAnnotationEvaluator} - to execute Python scripts synchronously or asynchronously.</li>
 *   <li>{@link PythonBeforeAspect} and {@link PythonAfterAspect} - to handle execution before and after method invocation.</li>
 * </ul>
 *
 *
 * @see PythonAspectProperties
 * @see PythonBeforeAspect
 * @see PythonAfterAspect
 * @see PythonBefore
 * @see PythonBefores
 * @see PythonAfter
 * @see PythonAfters
 * @see ProfileChecker
 * @see PythonAnnotationEvaluator
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(PythonAspectProperties.class)
public class PythonAspectAutoConfiguration {
    /**
     * Creates a default {@link ProfileChecker} implementation.
     *
     * <p>This bean is created only if no other {@link ProfileChecker} bean is present in the context.
     * It uses the active Spring {@link Environment} profiles to determine whether Python scripts
     * should be executed for a given method.
     *
     * @param environment non-null Spring {@link Environment} used to resolve active profiles
     * @return non-null {@link BasicProfileChecker} instance
     */
    @Bean
    @ConditionalOnMissingBean(ProfileChecker.class)
    public ProfileChecker profileChecker(Environment environment) {
        return new BasicProfileChecker(environment);
    }

    @Bean
    @ConditionalOnMissingBean(PythonAnnotationEvaluator.class)
    public PythonAnnotationEvaluator basicPythonAnnotationEvaluator(ProfileChecker profileChecker,
                                                                    PythonProcessor pythonProcessor) {
        return new BasicPythonAnnotationEvaluator(profileChecker, pythonProcessor);
    }

    /**
     * Creates an asynchronous wrapper for an existing {@link PythonAnnotationEvaluator}.
     *
     * @param annotationEvaluator non-null existing evaluator
     * @return non-null {@link AsyncPythonAnnotationEvaluator} instance
     */
    @Bean
    @Primary
    @ConditionalOnBean(PythonAnnotationEvaluator.class)
    @ConditionalOnBooleanProperty(name = "spring.python.aspect.async.enabled")
    public PythonAnnotationEvaluator asyncPythonAnnotationEvaluator(PythonAnnotationEvaluator annotationEvaluator,
                                                                    @Qualifier("pythonAspectTaskExecutor") TaskExecutor taskExecutor) {
        return new AsyncPythonAnnotationEvaluator(annotationEvaluator, taskExecutor);
    }

    /**
     * Creates the {@link TaskExecutor} for handling async executions.
     *
     * @param aspectProperties non-null configuration properties
     * @return non-null {@link TaskExecutor} instance
     */
    @Bean
    @ConditionalOnBooleanProperty(name = "spring.python.aspect.async.enabled")
    public TaskExecutor pythonAspectTaskExecutor(PythonAspectProperties aspectProperties) {
        var asyncProperties = aspectProperties.getAsync();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncProperties.getQueueCapacity());
        executor.setThreadNamePrefix(asyncProperties.getThreadNamePrefix());
        RejectedExecutionHandler handler = switch (asyncProperties.getRejectionPolicy()) {
            case CALLER_RUNS -> new ThreadPoolExecutor.CallerRunsPolicy();
            case ABORT -> new ThreadPoolExecutor.AbortPolicy();
            case DISCARD_OLDEST -> new ThreadPoolExecutor.DiscardOldestPolicy();
            case DISCARD -> new ThreadPoolExecutor.DiscardPolicy();
        };
        executor.setRejectedExecutionHandler(handler);
        executor.initialize();
        return executor;
    }

    /**
     * Creates the {@link PythonBeforeAspect} for handling {@code BEFORE} scope executions.
     *
     * @param annotationEvaluator non-null annotation evaluator
     * @return non-null {@link PythonBeforeAspect} instance
     */
    @Bean
    public PythonBeforeAspect pythonBeforeAspect(PythonAnnotationEvaluator annotationEvaluator) {
        return new PythonBeforeAspect(annotationEvaluator);
    }

    /**
     * Creates the {@link PythonAfterAspect} for handling {@code AFTER} scope executions.
     *
     * @param annotationEvaluator non-null annotation evaluator
     * @return non-null {@link PythonAfterAspect} instance
     */
    @Bean
    public PythonAfterAspect pythonAfterAspect(PythonAnnotationEvaluator annotationEvaluator) {
        return new PythonAfterAspect(annotationEvaluator);
    }
}