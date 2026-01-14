package io.maksymuimanov.python.metrics;

import io.maksymuimanov.python.processor.PythonContext;
import io.maksymuimanov.python.processor.PythonProcessor;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MeteredPythonProcessor implements PythonProcessor {
    public static final String ATTEMPT_COUNTER_KEY = "python.processor.attempts";
    public static final String ATTEMPT_COUNTER_DESCRIPTION = "Number of PythonProcessor invocation attempts";
    public static final String SUCCESS_COUNTER_KEY = "python.processor.success";
    public static final String SUCCESS_COUNTER_DESCRIPTION = "Number of successful PythonProcessor executions";
    public static final String ERROR_COUNTER_KEY = "python.processor.error";
    public static final String ERROR_COUNTER_DESCRIPTION = "Number of failed PythonProcessor executions";
    public static final String EXECUTION_TIMER_KEY = "python.processor.execution";
    public static final String EXECUTION_TIMER_DESCRIPTION = "PythonProcessor execution duration";
    public static final String SCRIPT_NAME_TAG_KEY = "script";
    private final PythonProcessor delegate;
    private final MeterRegistry meterRegistry;
    private final double @Nullable [] percentiles;
    private final Counter totalAttemptCounter;
    private final Counter totalSuccessCounter;
    private final Counter totalErrorCounter;
    private final Timer totalTimer;
    private final Map<String, Counter> attemptCounters;
    private final Map<String, Counter> successCounters;
    private final Map<String, Counter> errorCounters;
    private final Map<String, Timer> timers;

    public MeteredPythonProcessor(PythonProcessor delegate, MeterRegistry meterRegistry, double @Nullable ... percentiles) {
        this.delegate = delegate;
        this.meterRegistry = meterRegistry;
        this.percentiles = percentiles;
        this.totalAttemptCounter = Counter.builder(ATTEMPT_COUNTER_KEY)
                .description(ATTEMPT_COUNTER_DESCRIPTION)
                .register(this.meterRegistry);
        this.totalSuccessCounter = Counter.builder(SUCCESS_COUNTER_KEY)
                .description(SUCCESS_COUNTER_DESCRIPTION)
                .register(this.meterRegistry);
        this.totalErrorCounter = Counter.builder(ERROR_COUNTER_KEY)
                .description(ERROR_COUNTER_DESCRIPTION)
                .register(this.meterRegistry);
        this.totalTimer = Timer.builder(EXECUTION_TIMER_KEY)
                .description(EXECUTION_TIMER_DESCRIPTION)
                .publishPercentiles(this.percentiles)
                .register(this.meterRegistry);
        this.attemptCounters = new ConcurrentHashMap<>();
        this.successCounters = new ConcurrentHashMap<>();
        this.errorCounters = new ConcurrentHashMap<>();
        this.timers = new ConcurrentHashMap<>();
    }

    @Override
    public PythonResultMap process(PythonContext context) {
        this.totalAttemptCounter.increment();
        Counter attemptCounter = this.getScriptCounter(context, this.attemptCounters, ATTEMPT_COUNTER_KEY, ATTEMPT_COUNTER_DESCRIPTION);
        attemptCounter.increment();
        Timer.Sample totalSample = Timer.start(this.meterRegistry);
        Timer timer = this.getScriptTimer(context, EXECUTION_TIMER_KEY, EXECUTION_TIMER_DESCRIPTION);
        Timer.Sample sample = Timer.start(this.meterRegistry);
        try {
            PythonResultMap resultMap = this.delegate.process(context);
            this.totalSuccessCounter.increment();
            Counter successCounter = this.getScriptCounter(context, this.successCounters, SUCCESS_COUNTER_KEY, SUCCESS_COUNTER_DESCRIPTION);
            successCounter.increment();
            return resultMap;
        } catch (Exception e) {
            this.totalErrorCounter.increment();
            Counter errorCounter = this.getScriptCounter(context, this.errorCounters, ERROR_COUNTER_KEY, ERROR_COUNTER_DESCRIPTION);
            errorCounter.increment();
            throw e;
        } finally {
            sample.stop(timer);
            totalSample.stop(this.totalTimer);
        }
    }

    protected Counter getScriptCounter(PythonContext context, Map<String, Counter> counters, String counterName, String description) {
        PythonScript pythonScript = context.script();
        String source = pythonScript.getName();
        return counters.computeIfAbsent(source, (key) ->
                Counter.builder(counterName)
                        .description(description)
                        .tag(SCRIPT_NAME_TAG_KEY, key)
                        .register(this.meterRegistry));
    }

    protected Timer getScriptTimer(PythonContext context, String timerName, String description) {
        PythonScript pythonScript = context.script();
        String source = pythonScript.getName();
        return this.timers.computeIfAbsent(source, (key) ->
                Timer.builder(timerName)
                        .description(description)
                        .tag(SCRIPT_NAME_TAG_KEY, key)
                        .publishPercentiles(this.percentiles)
                        .register(this.meterRegistry));
    }
}
