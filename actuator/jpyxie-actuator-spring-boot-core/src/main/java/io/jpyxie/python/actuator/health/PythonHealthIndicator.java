package io.jpyxie.python.actuator.health;

import io.jpyxie.python.processor.PythonProcessor;
import io.jpyxie.python.script.PythonScript;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class PythonHealthIndicator extends AbstractHealthIndicator {
    public static final String PING_SCRIPT_KEY = "pingScript";
    public static final String PING_SCRIPT_NAME = "ping_script";
    public static final String PING_SCRIPT_VALUE = "print('Ping!')";
    private final PythonProcessor pythonProcessor;

    public PythonHealthIndicator(PythonProcessor pythonProcessor) {
        this.pythonProcessor = pythonProcessor;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up().withDetail(PING_SCRIPT_KEY, PING_SCRIPT_VALUE);
        PythonScript pingScript = PythonScript.fromString(PING_SCRIPT_NAME, PING_SCRIPT_VALUE);
        this.pythonProcessor.process(pingScript);
    }
}
