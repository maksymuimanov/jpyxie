package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;

class PythonLifecycleAutoConfigurationTest {
    private ApplicationContextRunner contextRunner;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(PythonLifecycleAutoConfiguration.class));
    }

    @Nested
    @SpringBootTest(classes = {ExistingBeansTest.Config.class, PythonLifecycleAutoConfiguration.class})
    class ExistingBeansTest {
        @Autowired
        private ConfigurableApplicationContext applicationContext;

        @Test
        void initialize_shouldCallAllInitializers_whenApplicationStarted() {
            assumeThat(applicationContext.getBeansOfType(PythonInitializer.class))
                    .hasSize(2);

            PythonInitializer mockInitializer1 = applicationContext.getBean("mockInitializer1", PythonInitializer.class);
            PythonInitializer mockInitializer2 = applicationContext.getBean("mockInitializer2", PythonInitializer.class);

            verify(mockInitializer1, times(1))
                    .initialize();
            verify(mockInitializer2, times(1))
                    .initialize();
        }

        @Configuration
        static class Config {
            @Bean
            public PythonInitializer mockInitializer1() {
                return mock(PythonInitializer.class);
            }

            @Bean
            public PythonInitializer mockInitializer2() {
                return mock(PythonInitializer.class);
            }

            @Bean
            public PythonFinalizer mockFinalizer1() {
                return mock(PythonFinalizer.class);
            }

            @Bean
            public PythonFinalizer mockFinalizer2() {
                return mock(PythonFinalizer.class);
            }
        }
    }

    @Nested
    @SpringBootTest(classes = PythonLifecycleAutoConfiguration.class)
    class NoBeansTest {
        @Autowired
        private ConfigurableApplicationContext applicationContext;

        @Test
        void initialize_shouldNotFail_whenNoInitializersExist() {
            assumeThat(applicationContext.getBeansOfType(PythonInitializer.class))
                    .isEmpty();
        }
    }

    @Test
    void finish_shouldCallAllFinalizers_whenContextClosed() {
        contextRunner.withUserConfiguration(ExistingBeansTest.Config.class)
                .run(context -> {
                    assumeThat(context.getBeansOfType(PythonFinalizer.class))
                            .hasSize(2);

                    PythonFinalizer mockFinalizer1 = context.getBean("mockFinalizer1", PythonFinalizer.class);
                    PythonFinalizer mockFinalizer2 = context.getBean("mockFinalizer2", PythonFinalizer.class);

                    context.close();

                    verify(mockFinalizer1, times(1))
                            .finish();
                    verify(mockFinalizer2, times(1))
                            .finish();
                });
    }

    @Test
    void finish_shouldNotFail_whenNoFinalizersExist() {
        contextRunner.run(context -> {
            assertThat(context)
                    .doesNotHaveBean(PythonFinalizer.class);

            context.close();
        });
    }
}
