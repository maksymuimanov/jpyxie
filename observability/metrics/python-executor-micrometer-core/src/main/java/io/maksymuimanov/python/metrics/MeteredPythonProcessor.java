package io.maksymuimanov.python.metrics;

import io.maksymuimanov.python.processor.PythonContext;
import io.maksymuimanov.python.processor.PythonProcessor;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

@Slf4j
public class MeteredPythonProcessor implements PythonProcessor {
    public static final String ATTEMPT_COUNTER_KEY = "python.processor.attempts";
    public static final String ATTEMPT_COUNTER_DESCRIPTION = "Number of python processor invocation attempts";
    public static final String SUCCESS_COUNTER_KEY = "python.processor.success";
    public static final String SUCCESS_COUNTER_DESCRIPTION = "Number of successful python processor executions";
    public static final String ERROR_COUNTER_KEY = "python.processor.error";
    public static final String ERROR_COUNTER_DESCRIPTION = "Number of failed python processor executions";
    public static final String EXECUTION_TIMER_KEY = "python.processor.execution";
    public static final String EXECUTION_TIMER_DESCRIPTION = "Python processor execution duration";
    private final PythonProcessor delegate;
    private final Counter attemptCounter;
    private final Counter successCounter;
    private final Counter errorCounter;
    private final Timer timer;

    public MeteredPythonProcessor(PythonProcessor delegate, MeterRegistry meterRegistry, double @Nullable ... percentiles) {
        this.delegate = delegate;
        this.attemptCounter = Counter.builder(ATTEMPT_COUNTER_KEY)
                .description(ATTEMPT_COUNTER_DESCRIPTION)
                .register(meterRegistry);
        this.successCounter = Counter.builder(SUCCESS_COUNTER_KEY)
                .description(SUCCESS_COUNTER_DESCRIPTION)
                .register(meterRegistry);
        this.errorCounter = Counter.builder(ERROR_COUNTER_KEY)
                .description(ERROR_COUNTER_DESCRIPTION)
                .register(meterRegistry);
        this.timer = Timer.builder(EXECUTION_TIMER_KEY)
                .description(EXECUTION_TIMER_DESCRIPTION)
                .publishPercentiles(percentiles)
                .register(meterRegistry);
    }

    @Override
    public PythonResultMap process(PythonContext context) {
        try {
            this.attemptCounter.increment();
            PythonResultMap resultMap = this.timer.record(() -> this.delegate.process(context));
            this.successCounter.increment();
            return resultMap;
        } catch (Exception e) {
            this.errorCounter.increment();
            throw e;
        }
    }
}
