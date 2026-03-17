package io.jpyxie.demo;

import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.interpreter.PoolPythonInterpreterProvider;
import io.jpyxie.python.interpreter.PythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.library.PipPythonLibraryManager;
import io.jpyxie.python.library.PythonLibraryManager;
import org.graalvm.polyglot.io.IOAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public PythonInterpreterProvider<?> interpreterProvider(PythonInterpreterFactory<?> factory) {
        return new PoolPythonInterpreterProvider<>(factory);
    }

    @Bean
    public IOAccess ioAccess() {
        return IOAccess.newBuilder()
                .allowHostFileAccess(true)
                .build();
    }

    @Bean
    public PythonLibraryManager pythonLibraryManager(PythonEnvironment environment) {
        return new PipPythonLibraryManager(environment);
    }
}
