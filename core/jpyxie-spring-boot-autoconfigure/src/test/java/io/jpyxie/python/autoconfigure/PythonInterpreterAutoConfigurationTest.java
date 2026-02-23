package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.interpreter.PythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInterpreterProviderFinalizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class PythonInterpreterAutoConfigurationTest {
    private ApplicationContextRunner contextRunner;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(PythonInterpreterAutoConfiguration.class));
    }

    @Test
    void pythonInterpreterProvider_shouldNotCreateBean_whenInterpreterFactoryMissing() {
        contextRunner.run(context -> {
            assertThat(context)
                    .doesNotHaveBean(PythonInterpreterProvider.class)
                    .doesNotHaveBean("pythonInterpreterProvider");
        });
    }

    @Test
    void pythonInterpreterProvider_shouldCreateBean_whenInterpreterFactoryExists() {
        contextRunner.withBean(PythonInterpreterFactory.class, TestPythonInterpreterFactory::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonInterpreterProvider.class)
                            .hasBean("pythonInterpreterProvider");
                });
    }

    @Test
    void pythonInterpreterProvider_shouldNotCreateBean_whenCustomProviderExists() {
        contextRunner.withBean(PythonInterpreterFactory.class, TestPythonInterpreterFactory::new)
                .withBean(PythonInterpreterProvider.class, TestPythonInterpreterProvider::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonInterpreterFactory.class)
                            .hasSingleBean(PythonInterpreterProvider.class);
                });
    }

    @Test
    void externalPythonLibraryFinalizer_shouldCreateBean_whenInterpreterProviderExists() {
        contextRunner.withBean(PythonInterpreterProvider.class, TestPythonInterpreterProvider::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonFinalizer.class)
                            .hasBean("externalPythonLibraryFinalizer");
                    PythonFinalizer finalizer = context.getBean(PythonFinalizer.class);
                    assertThat(finalizer)
                            .isInstanceOf(PythonInterpreterProviderFinalizer.class);
                });
    }

    @Test
    void externalPythonLibraryFinalizer_shouldNotCreateBean_whenInterpreterProviderMissing() {
        contextRunner.run(context -> {
            assertThat(context)
                    .doesNotHaveBean(PythonFinalizer.class);
        });
    }

    @Test
    void configuration_shouldCreateBothBeans_whenAllConditionsMet() {
        contextRunner.withBean(PythonInterpreterFactory.class, TestPythonInterpreterFactory::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonInterpreterProvider.class)
                            .hasSingleBean(PythonFinalizer.class)
                            .hasBean("pythonInterpreterProvider")
                            .hasBean("externalPythonLibraryFinalizer");
                });
    }
    
    private static class TestPythonInterpreterFactory implements PythonInterpreterFactory<AutoCloseable> {
        @Override
        public AutoCloseable create() {
            return null;
        }
    }
    
    private static class TestPythonInterpreterProvider implements PythonInterpreterProvider<AutoCloseable> {
        @Override
        public AutoCloseable acquire() {
            return null;
        }

        @Override
        public void close() throws Exception {
        }
    }
}
