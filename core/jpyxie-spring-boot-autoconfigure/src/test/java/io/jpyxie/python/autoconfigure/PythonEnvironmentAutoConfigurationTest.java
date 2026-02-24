package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.environment.VenvPythonEnvironment;
import io.jpyxie.python.lifecycle.PythonEnvironmentInitializer;
import io.jpyxie.python.lifecycle.PythonInitializer;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.nio.file.Path;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PythonEnvironmentAutoConfigurationTest {
    private ApplicationContextRunner contextRunner;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(PythonEnvironmentAutoConfiguration.class));
    }

    @Test
    void venvPythonEnvironment_shouldCreateBean_whenMissingBean() {
        contextRunner.run(context -> {
            assertThat(context)
                    .hasSingleBean(PythonEnvironment.class)
                    .hasBean("venvPythonEnvironment");
            PythonEnvironment environment = context.getBean(PythonEnvironment.class);
            assertThat(environment)
                    .isInstanceOf(VenvPythonEnvironment.class);
        });
    }

    @Test
    void venvPythonEnvironment_shouldNotCreateBean_whenCustomBeanExists() {
        contextRunner.withBean(PythonEnvironment.class, TestPythonEnvironment::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonEnvironment.class)
                            .doesNotHaveBean("venvPythonEnvironment");
                });
    }

    @Test
    void venvPythonEnvironment_shouldUseDefaultProperties() {
        contextRunner.run(context -> {
            PythonEnvironmentProperties properties = context.getBean(PythonEnvironmentProperties.class);
            assertThat(properties.isEnabled())
                    .isTrue();
            assertThat(properties.getGlobalPythonExecutable())
                    .isEqualTo("python");
            assertThat(properties.getBackupPythonExecutable())
                    .isEqualTo("python");
            assertThat(properties.getOnExisting())
                    .isEqualTo(PythonEnvironmentProperties.OnExisting.SKIP);
            assertThat(properties.getParentDirectory())
                    .isEqualTo(VenvPythonEnvironment.DEFAULT_VENV_PARENT_DIRECTORY);
            assertThat(properties.isRedirectErrorStream())
                    .isEqualTo(VenvPythonEnvironment.DEFAULT_REDIRECT_ERROR_STREAM);
            assertThat(properties.isRedirectOutputStream())
                    .isEqualTo(VenvPythonEnvironment.DEFAULT_REDIRECT_OUTPUT_STREAM);
            assertThat(properties.isReadOutput())
                    .isEqualTo(VenvPythonEnvironment.DEFAULT_READ_OUTPUT);
            assertThat(properties.getTimeout())
                    .isEqualTo(VenvPythonEnvironment.DEFAULT_TIMEOUT);
        });
    }

    @Test
    void venvPythonEnvironment_shouldUseCustomProperties() {
        contextRunner.withPropertyValues(
                "spring.python.environment.enabled=false",
                "spring.python.environment.global-python-executable=python3",
                "spring.python.environment.backup-python-executable=python3.9",
                "spring.python.environment.on-existing=REMOVE",
                "spring.python.environment.parent-directory=/custom/venv",
                "spring.python.environment.redirect-error-stream=true",
                "spring.python.environment.redirect-output-stream=false",
                "spring.python.environment.read-output=true",
                "spring.python.environment.timeout=PT60S"
        ).run(context -> {
            PythonEnvironmentProperties properties = context.getBean(PythonEnvironmentProperties.class);
            assertThat(properties.isEnabled())
                    .isFalse();
            assertThat(properties.getGlobalPythonExecutable())
                    .isEqualTo("python3");
            assertThat(properties.getBackupPythonExecutable())
                    .isEqualTo("python3.9");
            assertThat(properties.getOnExisting())
                    .isEqualTo(PythonEnvironmentProperties.OnExisting.REMOVE);
            assertThat(properties.getParentDirectory())
                    .isEqualTo("/custom/venv");
            assertThat(properties.isRedirectErrorStream())
                    .isTrue();
            assertThat(properties.isRedirectOutputStream())
                    .isFalse();
            assertThat(properties.isReadOutput())
                    .isTrue();
            assertThat(properties.getTimeout())
                    .isEqualTo(Duration.ofSeconds(60));
        });
    }

    @Test
    void pythonEnvironmentInitializer_shouldCreateBean_whenEnvironmentEnabled() {
        contextRunner.withBean(PythonEnvironment.class, TestPythonEnvironment::new)
                .withPropertyValues("spring.python.environment.enabled=true")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonInitializer.class)
                            .hasBean("pythonEnvironmentInitializer");
                    PythonInitializer initializer = context.getBean(PythonInitializer.class);
                    assertThat(initializer)
                            .isInstanceOf(PythonEnvironmentInitializer.class);
                });
    }

    @Test
    void pythonEnvironmentInitializer_shouldNotCreateBean_whenEnvironmentDisabled() {
        contextRunner.withBean(PythonEnvironment.class, TestPythonEnvironment::new)
                .withPropertyValues("spring.python.environment.enabled=false")
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(PythonInitializer.class)
                            .doesNotHaveBean("pythonEnvironmentInitializer");
                });
    }

    @Test
    void skipExistingHandler_shouldCreateBean_whenOnExistingIsSkip() {
        contextRunner.withPropertyValues("spring.python.environment.on-existing=SKIP")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonEnvironment.OnExistingHandler.class)
                            .hasBean("skipExistingHandler");
                    PythonEnvironment.OnExistingHandler handler = context.getBean(PythonEnvironment.OnExistingHandler.class);
                    assertThat(handler)
                            .isInstanceOf(VenvPythonEnvironment.SkipExistingHandler.class);
                });
    }

    @Test
    void failExistingHandler_shouldCreateBean_whenOnExistingIsFail() {
        contextRunner.withPropertyValues("spring.python.environment.on-existing=FAIL")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonEnvironment.OnExistingHandler.class)
                            .hasBean("failExistingHandler");
                    PythonEnvironment.OnExistingHandler handler = context.getBean(PythonEnvironment.OnExistingHandler.class);
                    assertThat(handler)
                            .isInstanceOf(VenvPythonEnvironment.FailExistingHandler.class);
                });
    }

    @Test
    void removeExistingHandler_shouldCreateBean_whenOnExistingIsRemove() {
        contextRunner.withPropertyValues("spring.python.environment.on-existing=REMOVE")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonEnvironment.OnExistingHandler.class)
                            .hasBean("removeExistingHandler");
                    PythonEnvironment.OnExistingHandler handler = context.getBean(PythonEnvironment.OnExistingHandler.class);
                    assertThat(handler)
                            .isInstanceOf(VenvPythonEnvironment.RemoveExistingHandler.class);
                });
    }

    @Test
    void configuration_shouldCreateAllBeans_whenEnvironmentEnabled() {
        contextRunner.withPropertyValues("spring.python.environment.enabled=true")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonEnvironment.class)
                            .hasSingleBean(PythonInitializer.class)
                            .hasSingleBean(PythonEnvironment.OnExistingHandler.class);
                });
    }

    @Test
    void configuration_shouldCreateOnlyEnvironment_whenEnvironmentDisabled() {
        contextRunner.withPropertyValues("spring.python.environment.enabled=false")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonEnvironment.class)
                            .doesNotHaveBean(PythonInitializer.class)
                            .hasSingleBean(PythonEnvironment.OnExistingHandler.class);
                });
    }

    private static class TestPythonEnvironment implements PythonEnvironment {
        @Override
        public void create() {
        }

        @Override
        public String getExecutableOrBackup() {
            return "";
        }

        @Override
        public String getExecutableOrElse(String executable) {
            return "";
        }

        @Override
        public void remove() {
        }

        @Override
        public boolean exists() {
            return false;
        }

        @Override
        public @NonNull String getExecutable() {
            return "python";
        }

        @Override
        public Path getPath() {
            return null;
        }
    }

    private static class TestPythonInitializer implements PythonInitializer {
        @Override
        public void initialize() {
        }
    }
}
