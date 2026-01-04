package io.maksymuimanov.python.actuator.health;

import io.maksymuimanov.python.processor.PythonProcessor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPythonHealthChecker implements PythonHealthChecker {
    private final PythonProcessor pythonProcessor;

    @Override
    public void check() {
        this.pythonProcessor.process(PING_SCRIPT);
    }
}
