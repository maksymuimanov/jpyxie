package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.library.BasicPipManager;
import io.jpyxie.python.library.PipManager;
import io.jpyxie.python.library.PythonLibrary;
import io.jpyxie.python.lifecycle.ExternalPythonLibraryFinalizer;
import io.jpyxie.python.lifecycle.ExternalPythonLibraryInitializer;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInitializer;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PipAutoConfigurationTest {
    private ApplicationContextRunner contextRunner;
    @Mock
    private PythonLibrary mockLibrary1;
    @Mock
    private PythonLibrary mockLibrary2;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(PipAutoConfiguration.class));
    }

    @Test
    void basicPipManager_shouldCreateBean_whenMissingBean() {
        contextRunner.run(context -> {
            assertThat(context)
                    .hasSingleBean(PipManager.class)
                    .hasBean("basicPipManager");
            PipManager manager = context.getBean(PipManager.class);
            assertThat(manager)
                    .isInstanceOf(BasicPipManager.class);
        });
    }

    @Test
    void basicPipManager_shouldNotCreateBean_whenCustomBeanExists() {
        contextRunner.withBean(PipManager.class, TestPipManager::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PipManager.class)
                            .doesNotHaveBean("basicPipManager");
                });
    }

    @Test
    void basicPipManager_shouldUseDefaultProperties() {
        contextRunner.run(context -> {
            PythonPipProperties properties = context.getBean(PythonPipProperties.class);
            assertThat(properties.getCommand())
                    .isEqualTo(BasicPipManager.DEFAULT_COMMAND);
            assertThat(properties.isRedirectErrorStream())
                    .isEqualTo(BasicPipManager.DEFAULT_REDIRECT_ERROR_STREAM);
            assertThat(properties.isRedirectOutputStream())
                    .isEqualTo(BasicPipManager.DEFAULT_REDIRECT_OUTPUT_STREAM);
            assertThat(properties.isReadOutput())
                    .isEqualTo(BasicPipManager.DEFAULT_READ_OUTPUT);
            assertThat(properties.getTimeout())
                    .isEqualTo(BasicPipManager.DEFAULT_TIMEOUT);
        });
    }

    @Test
    void basicPipManager_shouldUseCustomProperties() {
        contextRunner.withPropertyValues(
                "spring.python.pip.command=custom-pip",
                "spring.python.pip.redirect-error-stream=true",
                "spring.python.pip.redirect-output-stream=false",
                "spring.python.pip.read-output=true",
                "spring.python.pip.timeout=PT30S"
        ).run(context -> {
            PythonPipProperties properties = context.getBean(PythonPipProperties.class);
            assertThat(properties.getCommand())
                    .isEqualTo("custom-pip");
            assertThat(properties.isRedirectErrorStream())
                    .isTrue();
            assertThat(properties.isRedirectOutputStream())
                    .isFalse();
            assertThat(properties.isReadOutput())
                    .isTrue();
            assertThat(properties.getTimeout())
                    .isEqualTo(Duration.ofSeconds(30));
        });
    }

    @Test
    void externalPythonLibraryInitializer_shouldCreateBean_whenLibraryEnabled() {
        contextRunner.withBean(PipManager.class, TestPipManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=true")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonInitializer.class)
                            .hasBean("externalPythonLibraryInitializer");
                    PythonInitializer initializer = context.getBean(PythonInitializer.class);
                    assertThat(initializer)
                            .isInstanceOf(ExternalPythonLibraryInitializer.class);
                });
    }

    @Test
    void externalPythonLibraryInitializer_shouldNotCreateBean_whenLibraryDisabled() {
        contextRunner.withBean(PipManager.class, TestPipManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=false")
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(PythonInitializer.class)
                            .doesNotHaveBean("externalPythonLibraryInitializer");
                });
    }

    @Test
    void externalPythonLibraryFinalizer_shouldCreateBean_whenLibraryEnabled() {
        contextRunner.withBean(PipManager.class, TestPipManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=true")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonFinalizer.class)
                            .hasBean("externalPythonLibraryFinalizer");
                    PythonFinalizer finalizer = context.getBean(PythonFinalizer.class);
                    assertThat(finalizer)
                            .isInstanceOf(ExternalPythonLibraryFinalizer.class);
                });
    }

    @Test
    void externalPythonLibraryFinalizer_shouldNotCreateBean_whenLibraryDisabled() {
        contextRunner.withBean(PipManager.class, TestPipManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=false")
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(PythonFinalizer.class)
                            .doesNotHaveBean("externalPythonLibraryFinalizer");
                });
    }

    @Test
    void libraryProperties_shouldHaveDefaultValues() {
        contextRunner.run(context -> {
            PythonPipProperties properties = context.getBean(PythonPipProperties.class);
            PythonPipProperties.LibraryProperties libraryProperties = properties.getLibrary();
            assertThat(libraryProperties.isEnabled())
                    .isFalse();
            assertThat(libraryProperties.getInstalled())
                    .isEmpty();
            assertThat(libraryProperties.getUninstalled())
                    .isEmpty();
        });
    }

    @Test
    void libraryProperties_shouldBindCustomValues() {
        contextRunner.withPropertyValues("spring.python.pip.library.enabled=true")
                .run(context -> {
                    PythonPipProperties properties = context.getBean(PythonPipProperties.class);
                    PythonPipProperties.LibraryProperties libraryProperties = properties.getLibrary();
                    assertThat(libraryProperties.isEnabled())
                            .isTrue();
                });
    }

    @Test
    void configuration_shouldCreateAllBeans_whenLibraryEnabled() {
        contextRunner.withBean(PipManager.class, TestPipManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=true")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PipManager.class)
                            .hasSingleBean(PythonInitializer.class)
                            .hasSingleBean(PythonFinalizer.class);
                });
    }

    @Test
    void configuration_shouldCreateOnlyPipManager_whenLibraryDisabled() {
        contextRunner.withBean(PipManager.class, TestPipManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=false")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PipManager.class)
                            .doesNotHaveBean(PythonInitializer.class)
                            .doesNotHaveBean(PythonFinalizer.class);
                });
    }

    private static class TestPipManager implements PipManager {
        @Override
        public boolean exists(@NonNull PythonLibrary management) {
            return false;
        }

        @Override
        public void install(@NonNull PythonLibrary management) {
        }

        @Override
        public void uninstall(@NonNull PythonLibrary management) {
        }
    }
}
