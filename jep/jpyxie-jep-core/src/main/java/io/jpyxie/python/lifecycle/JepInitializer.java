package io.jpyxie.python.lifecycle;

import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.exception.PythonLifecycleException;
import jep.MainInterpreter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RequiredArgsConstructor
public class JepInitializer implements PythonInitializer {
    private final PythonEnvironment pythonEnvironment;
    private final String pythonLibraryPath;

    //TODO
    @Override
    public void initialize() {
        try {
            log.info("Starting JEP initialization");
            Path path = this.pythonEnvironment.getPath();
            if (!Files.exists(path)) {
                PythonLifecycleException exception = new PythonLifecycleException("Python environment path [%s] does not exist".formatted(path));
                log.error(exception.getMessage(), exception);
                throw exception;
            } else {
                log.info("Python environment path [{}] exists", path);
                log.debug("Loading python to [{}]", this.pythonLibraryPath);
                System.load(this.pythonLibraryPath);
                log.debug("Loading jep to [{}]", this.pythonLibraryPath);
                Path jepDllPath = this.locateJepDll(path);
                MainInterpreter.setJepLibraryPath(jepDllPath.toString());

                log.debug("Set jep library path to [{}]", jepDllPath);
            }
            log.info("JEP initialization is finished");
        } catch (Exception e) {
            PythonLifecycleException exception = new PythonLifecycleException("Failed to initialize JEP", e);
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    private Path locateJepDll(Path path) {
        return path.toAbsolutePath()
                .resolve("Lib")
                .resolve("site-packages")
                .resolve("jep")
                .resolve("jep.dll");
    }

    @Override
    public int getPriority() {
        return LOW_PRIORITY;
    }
}