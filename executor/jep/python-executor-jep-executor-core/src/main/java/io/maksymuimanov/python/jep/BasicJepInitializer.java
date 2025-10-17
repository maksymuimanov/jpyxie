package io.maksymuimanov.python.jep;

import jep.MainInterpreter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicJepInitializer implements JepInitializer {
    private final String path;

    @Override
    public void initialize() {
        MainInterpreter.setJepLibraryPath();
    }
}
