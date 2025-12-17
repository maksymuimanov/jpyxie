package io.maksymuimanov.python.lifecycle;

import io.maksymuimanov.python.exception.PythonLifecycleException;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PythonInterpreterProviderFinalizer implements PythonFinalizer {
    private final PythonInterpreterProvider<?> provider;

    @Override
    public void finish() {
        String providerName = provider.getClass().getSimpleName();
        try {
            log.info("Closing Python interpreter provider: [{}]", providerName);
            provider.close();
            log.info("Python interpreter provider closed: [{}]", providerName);
        } catch (Exception e) {
            log.error("Error while closing Python interpreter provider [{}]", providerName, e);
            throw new PythonLifecycleException(e);
        }
    }
}
