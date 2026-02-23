package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.resolver.BasicPythonResolverHolder;
import io.jpyxie.python.resolver.PythonArgumentSpec;
import io.jpyxie.python.resolver.PythonResolver;
import io.jpyxie.python.resolver.PythonResolverHolder;
import io.jpyxie.python.script.PythonScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PythonResolverAutoConfigurationTest {
    private ApplicationContextRunner contextRunner;
    @Mock
    private PythonResolver mockResolver1;
    @Mock
    private PythonResolver mockResolver2;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(PythonResolverAutoConfiguration.class));
    }

    @Test
    void basicPythonResolverHolder_shouldCreateBean_whenNoCustomHolderExists() {
        contextRunner
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonResolverHolder.class)
                            .hasBean("basicPythonResolverHolder");
                    PythonResolverHolder holder = context.getBean(PythonResolverHolder.class);
                    assertThat(holder)
                            .isInstanceOf(BasicPythonResolverHolder.class);
                });
    }

    @Test
    void basicPythonResolverHolder_shouldNotCreateBean_whenCustomHolderExists() {
        contextRunner
                .withBean(PythonResolverHolder.class, TestPythonResolverHolder::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonResolverHolder.class)
                            .doesNotHaveBean("basicPythonResolverHolder");
                });
    }

    @Test
    void basicPythonResolverHolder_shouldInjectAllResolvers_whenMultipleResolversExist() {
        contextRunner
                .withBean("resolver1", PythonResolver.class, () -> mockResolver1)
                .withBean("resolver2", PythonResolver.class, () -> mockResolver2)
                .run(context -> {
                    PythonResolverHolder holder = context.getBean(PythonResolverHolder.class);
                    assertThat(holder)
                            .isInstanceOf(BasicPythonResolverHolder.class);
                });
    }

    @Test
    void basicPythonResolverHolder_shouldCreateWithEmptyList_whenNoResolversExist() {
        contextRunner
                .run(context -> {
                    PythonResolverHolder holder = context.getBean(PythonResolverHolder.class);
                    assertThat(holder)
                            .isInstanceOf(BasicPythonResolverHolder.class);
                });
    }

    @Test
    void basicPythonResolverHolder_shouldCreateWithSingleResolver_whenOneResolverExists() {
        contextRunner
                .withBean(PythonResolver.class, () -> mockResolver1)
                .run(context -> {
                    PythonResolverHolder holder = context.getBean(PythonResolverHolder.class);
                    assertThat(holder)
                            .isInstanceOf(BasicPythonResolverHolder.class);
                });
    }

    @Test
    void basicPythonResolverHolder_shouldNotDependOnResolverBeans() {
        contextRunner
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonResolverHolder.class)
                            .doesNotHaveBean(PythonResolver.class);
                });
    }

    @Test
    void basicPythonResolverHolder_shouldCreateBeanRegardlessOfResolverPresence() {
        contextRunner
                .withBean(PythonResolver.class, () -> mockResolver1)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonResolverHolder.class)
                            .hasSingleBean(PythonResolver.class);
                });

        contextRunner
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonResolverHolder.class)
                            .doesNotHaveBean(PythonResolver.class);
                });
    }

    @Test
    void basicPythonResolverHolder_shouldUseListInjection() {
        contextRunner
                .withBean("resolver1", PythonResolver.class, () -> mockResolver1)
                .withBean("resolver2", PythonResolver.class, () -> mockResolver2)
                .run(context -> {
                    List<PythonResolver> resolvers = context.getBeansOfType(PythonResolver.class)
                            .values()
                            .stream()
                            .toList();
                    assertThat(resolvers)
                            .hasSize(2)
                            .contains(mockResolver1, mockResolver2);
                });
    }
    
    private static class TestPythonResolverHolder implements PythonResolverHolder {
        @Override
        public PythonScript resolveAll(PythonScript script, PythonArgumentSpec argumentSpec) {
            return null;
        }

        @Override
        public List<PythonResolver> getResolvers() {
            return List.of();
        }
    }
}
