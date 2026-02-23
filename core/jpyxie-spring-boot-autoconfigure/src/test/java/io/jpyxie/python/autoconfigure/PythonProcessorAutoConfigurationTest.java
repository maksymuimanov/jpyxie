package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.executor.PythonResultSpec;
import io.jpyxie.python.file.PythonFileReader;
import io.jpyxie.python.processor.BasicPythonProcessor;
import io.jpyxie.python.processor.PythonContext;
import io.jpyxie.python.processor.PythonProcessor;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.resolver.PythonArgumentSpec;
import io.jpyxie.python.resolver.PythonResolver;
import io.jpyxie.python.resolver.PythonResolverHolder;
import io.jpyxie.python.script.PythonScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PythonProcessorAutoConfigurationTest {
    private ApplicationContextRunner contextRunner;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(PythonProcessorAutoConfiguration.class));
    }

    @Test
    void basicPythonProcessor_shouldCreateBean_whenDependenciesExist() {
        contextRunner
                .withBean(PythonFileReader.class, TestPythonFileReader::new)
                .withBean(PythonExecutor.class, TestPythonExecutor::new)
                .withBean(PythonResolverHolder.class, TestPythonResolverHolder::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonProcessor.class)
                            .hasBean("basicPythonProcessor");
                    PythonProcessor processor = context.getBean(PythonProcessor.class);
                    assertThat(processor)
                            .isInstanceOf(BasicPythonProcessor.class);
                });
    }

    @Test
    void basicPythonProcessor_shouldNotCreateBean_whenCustomProcessorExists() {
        contextRunner
                .withBean(PythonFileReader.class, TestPythonFileReader::new)
                .withBean(PythonExecutor.class, TestPythonExecutor::new)
                .withBean(PythonResolverHolder.class, TestPythonResolverHolder::new)
                .withBean(PythonProcessor.class, TestPythonProcessor::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonProcessor.class)
                            .doesNotHaveBean("basicPythonProcessor");
                });
    }

    @Test
    void basicPythonProcessor_shouldNotCreateBean_whenPythonFileReaderMissing() {
        contextRunner
                .withBean(PythonExecutor.class, TestPythonExecutor::new)
                .withBean(PythonResolverHolder.class, TestPythonResolverHolder::new)
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(PythonProcessor.class)
                            .doesNotHaveBean("basicPythonProcessor");
                });
    }

    @Test
    void basicPythonProcessor_shouldNotCreateBean_whenPythonExecutorMissing() {
        contextRunner
                .withBean(PythonFileReader.class, TestPythonFileReader::new)
                .withBean(PythonResolverHolder.class, TestPythonResolverHolder::new)
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(PythonProcessor.class)
                            .doesNotHaveBean("basicPythonProcessor");
                });
    }

    @Test
    void basicPythonProcessor_shouldNotCreateBean_whenPythonResolverHolderMissing() {
        contextRunner
                .withBean(PythonFileReader.class, TestPythonFileReader::new)
                .withBean(PythonExecutor.class, TestPythonExecutor::new)
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(PythonProcessor.class)
                            .doesNotHaveBean("basicPythonProcessor");
                });
    }

    @Test
    void configuration_shouldLoadAfterDependencies() {
        contextRunner
                .withConfiguration(AutoConfigurations.of(PythonFileAutoConfiguration.class, PythonResolverAutoConfiguration.class))
                .withBean(PythonExecutor.class, TestPythonExecutor::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonProcessor.class)
                            .hasBean("basicPythonProcessor");
                });
    }

    @Test
    void basicPythonProcessor_shouldWireDependenciesCorrectly() {
        contextRunner
                .withBean(PythonFileReader.class, TestPythonFileReader::new)
                .withBean(PythonExecutor.class, TestPythonExecutor::new)
                .withBean(PythonResolverHolder.class, TestPythonResolverHolder::new)
                .run(context -> {
                    BasicPythonProcessor processor = (BasicPythonProcessor) context.getBean(PythonProcessor.class);
                    assertThat(processor)
                            .isNotNull();
                });
    }

    private static class TestPythonProcessor implements PythonProcessor {
        @Override
        public PythonResultMap process(PythonContext context) {
            return null;
        }
    }
    
    private static class TestPythonFileReader implements PythonFileReader {
        @Override
        public PythonScript readScript(PythonScript pythonScript) {
            return null;
        }
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
    
    private static class TestPythonExecutor implements PythonExecutor {
        @Override
        public PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec) {
            return null;
        }
    }
}
