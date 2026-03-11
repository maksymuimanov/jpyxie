package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.library.PythonLibrary;
import io.jpyxie.python.library.PythonLibraryManager;
import io.jpyxie.python.library.SubprocessPythonLibraryManager;
import io.jpyxie.python.lifecycle.PythonFinalizer;
import io.jpyxie.python.lifecycle.PythonInitializer;
import io.jpyxie.python.lifecycle.PythonLibraryFinalizer;
import io.jpyxie.python.lifecycle.PythonLibraryInitializer;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PythonLibraryAutoConfigurationTest {
    private ApplicationContextRunner contextRunner;
    @Mock
    private PythonLibrary mockLibrary1;
    @Mock
    private PythonLibrary mockLibrary2;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(PythonLibraryAutoConfiguration.class));
    }

    @Test
    void subprocessPythonLibraryManager_shouldCreateBean_whenMissingBean() {
        contextRunner.run(context -> {
            assertThat(context)
                    .hasSingleBean(PythonLibraryManager.class)
                    .hasBean("subprocessPythonLibraryManager");
            PythonLibraryManager manager = context.getBean(PythonLibraryManager.class);
            assertThat(manager)
                    .isInstanceOf(SubprocessPythonLibraryManager.class);
        });
    }

    @Test
    void subprocessPythonLibraryManager_shouldNotCreateBean_whenCustomBeanExists() {
        contextRunner.withBean(PythonLibraryManager.class, TestPythonLibraryManager::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonLibraryManager.class)
                            .doesNotHaveBean("subprocessPythonLibraryManager");
                });
    }

    @Test
    void externalPythonLibraryInitializer_shouldCreateBean_whenLibraryEnabled() {
        contextRunner.withBean(PythonLibraryManager.class, TestPythonLibraryManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=true")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonInitializer.class)
                            .hasBean("externalPythonLibraryInitializer");
                    PythonInitializer initializer = context.getBean(PythonInitializer.class);
                    assertThat(initializer)
                            .isInstanceOf(PythonLibraryInitializer.class);
                });
    }

    @Test
    void externalPythonLibraryInitializer_shouldNotCreateBean_whenLibraryDisabled() {
        contextRunner.withBean(PythonLibraryManager.class, TestPythonLibraryManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=false")
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(PythonInitializer.class)
                            .doesNotHaveBean("externalPythonLibraryInitializer");
                });
    }

    @Test
    void externalPythonLibraryFinalizer_shouldCreateBean_whenLibraryEnabled() {
        contextRunner.withBean(PythonLibraryManager.class, TestPythonLibraryManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=true")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonFinalizer.class)
                            .hasBean("externalPythonLibraryFinalizer");
                    PythonFinalizer finalizer = context.getBean(PythonFinalizer.class);
                    assertThat(finalizer)
                            .isInstanceOf(PythonLibraryFinalizer.class);
                });
    }

    @Test
    void externalPythonLibraryFinalizer_shouldNotCreateBean_whenLibraryDisabled() {
        contextRunner.withBean(PythonLibraryManager.class, TestPythonLibraryManager::new)
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
            PythonLibraryProperties properties = context.getBean(PythonLibraryProperties.class);
            PythonLibraryProperties.LibraryProperties libraryProperties = properties.getLibrary();
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
                    PythonLibraryProperties properties = context.getBean(PythonLibraryProperties.class);
                    PythonLibraryProperties.LibraryProperties libraryProperties = properties.getLibrary();
                    assertThat(libraryProperties.isEnabled())
                            .isTrue();
                });
    }

    @Test
    void configuration_shouldCreateAllBeans_whenLibraryEnabled() {
        contextRunner.withBean(PythonLibraryManager.class, TestPythonLibraryManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=true")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonLibraryManager.class)
                            .hasSingleBean(PythonInitializer.class)
                            .hasSingleBean(PythonFinalizer.class);
                });
    }

    @Test
    void configuration_shouldCreateOnlyPipManager_whenLibraryDisabled() {
        contextRunner.withBean(PythonLibraryManager.class, TestPythonLibraryManager::new)
                .withPropertyValues("spring.python.pip.library.enabled=false")
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonLibraryManager.class)
                            .doesNotHaveBean(PythonInitializer.class)
                            .doesNotHaveBean(PythonFinalizer.class);
                });
    }

    private static class TestPythonLibraryManager implements PythonLibraryManager {
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
