package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.file.BasicPythonFileReader;
import io.jpyxie.python.file.ClassPathResourcePythonFileInputStreamProvider;
import io.jpyxie.python.file.PythonFileInputStreamProvider;
import io.jpyxie.python.file.PythonFileReader;
import io.jpyxie.python.script.PythonScript;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class PythonFileAutoConfigurationTest {
    private ApplicationContextRunner contextRunner;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(PythonFileAutoConfiguration.class));
    }

    @Test
    void classPathResourceInputStreamProvider_shouldCreateBean_whenMissingBean() {
        contextRunner.run(context -> {
            assertThat(context)
                    .hasSingleBean(PythonFileInputStreamProvider.class)
                    .hasBean("classPathResourceInputStreamProvider");
            PythonFileInputStreamProvider provider = context.getBean(PythonFileInputStreamProvider.class);
            assertThat(provider)
                    .isInstanceOf(ClassPathResourcePythonFileInputStreamProvider.class);
        });
    }

    @Test
    void classPathResourceInputStreamProvider_shouldNotCreateBean_whenCustomBeanExists() {
        contextRunner.withBean(PythonFileInputStreamProvider.class, TestPythonFileInputStreamProvider::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonFileInputStreamProvider.class)
                            .doesNotHaveBean("classPathResourceInputStreamProvider");
                    PythonFileInputStreamProvider provider = context.getBean(PythonFileInputStreamProvider.class);
                    assertThat(provider)
                            .isInstanceOf(TestPythonFileInputStreamProvider.class);
                });
    }

    @Test
    void basicPythonFileHandler_shouldCreateBean_whenMissingBean() {
        contextRunner.run(context -> {
            assertThat(context)
                    .hasSingleBean(PythonFileReader.class)
                    .hasBean("basicPythonFileHandler");
            PythonFileReader reader = context.getBean(PythonFileReader.class);
            assertThat(reader)
                    .isInstanceOf(BasicPythonFileReader.class);
        });
    }

    @Test
    void basicPythonFileHandler_shouldNotCreateBean_whenCustomBeanExists() {
        contextRunner.withBean(PythonFileReader.class, TestPythonFileReader::new)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(PythonFileReader.class)
                            .doesNotHaveBean("basicPythonFileHandler");
                    PythonFileReader reader = context.getBean(PythonFileReader.class);
                    assertThat(reader)
                            .isInstanceOf(TestPythonFileReader.class);
                });
    }

    @Test
    void pythonFileProperties_shouldCreateBean_withDefaultValues() {
        contextRunner.run(context -> {
            assertThat(context)
                    .hasSingleBean(PythonFileProperties.class);
            PythonFileProperties properties = context.getBean(PythonFileProperties.class);
            assertThat(properties.getPath())
                    .isEqualTo(PythonFileInputStreamProvider.DEFAULT_PARENT_DIRECTORY);
            assertThat(properties.getCharset())
                    .isEqualTo(BasicPythonFileReader.DEFAULT_CHARSET_NAME);
        });
    }

    @Test
    void configuration_shouldEnableConfigurationProperties() {
        contextRunner.withPropertyValues("spring.python.file.path=/custom/path/", "spring.python.file.charset=ISO-8859-1")
                .run(context -> {
                    PythonFileProperties properties = context.getBean(PythonFileProperties.class);
                    assertThat(properties.getPath())
                            .isEqualTo("/custom/path/");
                    assertThat(properties.getCharset())
                            .isEqualTo("ISO-8859-1");
                });
    }

    private static class TestPythonFileReader implements PythonFileReader {
        @Override
        public PythonScript readScript(@NonNull PythonScript pythonScript) {
            return null;
        }
    }

    private static class TestPythonFileInputStreamProvider implements PythonFileInputStreamProvider {
        @Override
        public InputStream open(CharSequence path) {
            return null;
        }
    }
}
