package io.jpyxie.python.file;

import io.jpyxie.python.autoconfigure.PythonFileProperties;
import io.jpyxie.python.exception.PythonFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassPathResourcePythonFileInputStreamProviderTest {
    private ClassPathResourcePythonFileInputStreamProvider provider;
    @Mock
    private PythonFileProperties fileProperties;
    @Mock
    private Environment environment;

    @BeforeEach
    void setUp() {
        provider = new ClassPathResourcePythonFileInputStreamProvider(fileProperties, environment);
    }

    @Test
    void open_shouldReturnInputStream_whenFileExists() {
        when(fileProperties.getPath())
                .thenReturn("/python/");

        InputStream result = provider.open("test.py");

        assertThat(result)
                .isNotNull();
        verify(fileProperties)
                .getPath();
    }

    @Test
    void open_shouldTryProfileSpecificFile_whenMainFileNotFound() {
        when(fileProperties.getPath())
                .thenReturn("/python/");
        when(environment.getActiveProfiles())
                .thenReturn(new String[]{"dev"});

        InputStream result = provider.open("dev.py");

        assertThat(result)
                .isNotNull();
        verify(fileProperties)
                .getPath();
        verify(environment)
                .getActiveProfiles();
    }

    @Test
    void open_shouldTryAllProfiles_whenFileNotFound() {
        when(fileProperties.getPath())
                .thenReturn("/python/");
        when(environment.getActiveProfiles())
                .thenReturn(new String[]{"prod", "dev"});

        InputStream result = provider.open("dev.py");

        assertThat(result)
                .isNotNull();
        verify(fileProperties)
                .getPath();
        verify(environment)
                .getActiveProfiles();
    }

    @Test
    void open_shouldThrowPythonFileException_whenFileNotFoundInAllLocations() {
        when(fileProperties.getPath())
                .thenReturn("/nonexistent/");
        when(environment.getActiveProfiles())
                .thenReturn(new String[]{"dev", "prod"});

        assertThatThrownBy(() -> provider.open("nonexistent.py"))
                .isInstanceOf(PythonFileException.class);
        
        verify(fileProperties)
                .getPath();
        verify(environment)
                .getActiveProfiles();
    }

    @Test
    void open_shouldHandleEmptyPath() {
        assertThatThrownBy(() -> provider.open(""))
                .isInstanceOf(PythonFileException.class)
                .hasMessage("Path cannot be empty");
        
        verify(fileProperties, never())
                .getPath();
        verify(environment, never())
                .getActiveProfiles();
    }

    @Test
    void open_shouldUseCustomPath() {
        when(fileProperties.getPath())
                .thenReturn("/custom/");
        when(environment.getActiveProfiles())
                .thenReturn(new String[]{"dev"});

        InputStream result = provider.open("custom.py");

        assertThat(result)
                .isNotNull();
        verify(fileProperties)
                .getPath();
        verify(environment)
                .getActiveProfiles();
    }
}
