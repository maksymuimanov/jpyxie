package io.jpyxie.demo;

import io.jpyxie.python.interpreter.PythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.interpreter.SingletonPythonInterpreterProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JPyxieJepDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JPyxieJepDemoApplication.class, args);
    }

    @Bean
    public PythonInterpreterProvider<?> interpreterProvider(PythonInterpreterFactory<?> factory) {
        return new SingletonPythonInterpreterProvider<>(factory);
    }
}
