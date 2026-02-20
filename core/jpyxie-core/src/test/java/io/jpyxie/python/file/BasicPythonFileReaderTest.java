package io.jpyxie.python.file;

import io.jpyxie.python.exception.PythonFileException;
import io.jpyxie.python.script.PythonScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicPythonFileReaderTest {
    private BasicPythonFileReader fileReader;
    @Mock
    private InputStreamProvider inputStreamProvider;
    private PythonScript testScript;

    @BeforeEach
    void setUp() {
        Map<String, String> fileCache = new ConcurrentHashMap<>();
        fileReader = new BasicPythonFileReader(fileCache, inputStreamProvider, StandardCharsets.UTF_8);
        testScript = PythonScript.asFile("test_script", "test_script.py");
    }

    @Test
    void readScript_shouldReturnScriptAsIs_whenNotFile() {
        PythonScript nonFileScript = PythonScript.asString("test_script", "print('Hello World')");

        PythonScript result = fileReader.readScript(nonFileScript);

        assertThat(result)
                .isSameAs(nonFileScript);
        verifyNoInteractions(inputStreamProvider);
    }

    @Test
    @SuppressWarnings("resource")
    void readScript_shouldLoadFileContent_whenFile() {
        String expectedContent = "print('Hello World')";
        InputStream mockInputStream = new ByteArrayInputStream(expectedContent.getBytes(StandardCharsets.UTF_8));

        when(inputStreamProvider.open("test_script.py"))
                .thenReturn(mockInputStream);

        PythonScript result = fileReader.readScript(testScript);

        assertThat(result.source())
                .isEqualTo("test_script.py");
        assertThat(result.toPythonString().trim())
                .isEqualTo(expectedContent);
        verify(inputStreamProvider)
                .open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScript_shouldCacheFileContent() {
        String expectedContent = "print('cached content')";
        InputStream mockInputStream = new ByteArrayInputStream(expectedContent.getBytes(StandardCharsets.UTF_8));
        PythonScript script1 = PythonScript.asFile("script1", "test_script.py");
        PythonScript script2 = PythonScript.asFile("script2", "test_script.py");

        when(inputStreamProvider.open("test_script.py"))
                .thenReturn(mockInputStream);

        PythonScript result1 = fileReader.readScript(script1);
        PythonScript result2 = fileReader.readScript(script2);

        assertThat(result1)
                .isSameAs(script1)
                .isNotSameAs(result2)
                .extracting("source")
                .isEqualTo("test_script.py");
        assertThat(result2)
                .isSameAs(script2)
                .isNotSameAs(result1)
                .extracting("source")
                .isEqualTo("test_script.py");
        assertThat(result1.toPythonString().trim())
                .isEqualTo(expectedContent);
        assertThat(result2.toPythonString().trim())
                .isEqualTo(expectedContent);
        verify(inputStreamProvider, times(1))
                .open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScript_shouldThrowPythonFileException_whenInputStreamFails() {
        when(inputStreamProvider.open("test_script.py"))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> fileReader.readScript(testScript))
            .isInstanceOf(PythonFileException.class)
            .hasCauseInstanceOf(RuntimeException.class);
        verify(inputStreamProvider)
                .open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScript_shouldThrowPythonFileException_whenReadingBytesFails() throws IOException {
        InputStream mockInputStream = mock(InputStream.class);

        when(inputStreamProvider.open("test_script.py"))
                .thenReturn(mockInputStream);
        when(mockInputStream.readAllBytes())
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> fileReader.readScript(testScript))
                .isInstanceOf(PythonFileException.class)
                .hasCauseInstanceOf(RuntimeException.class);
        verify(inputStreamProvider)
                .open("test_script.py");
        verify(mockInputStream)
                .close();
    }

    @Test
    @SuppressWarnings("resource")
    void readScript_shouldHandleEmptyFile() {
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);

        when(inputStreamProvider.open("test_script.py"))
                .thenReturn(emptyStream);

        PythonScript result = fileReader.readScript(testScript);

        assertThat(result.toPythonString())
                .isEmpty();
        verify(inputStreamProvider)
                .open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScript_shouldAppendContentToExistingScript() {
        String newContent = "new line";
        InputStream mockInputStream = new ByteArrayInputStream(newContent.getBytes(StandardCharsets.UTF_8));
        PythonScript scriptWithContent = PythonScript.asFile("test_script", "test_script.py");

        when(inputStreamProvider.open("test_script.py"))
                .thenReturn(mockInputStream);

        PythonScript result = fileReader.readScript(scriptWithContent);

        assertThat(result.toPythonString().trim())
                .isEqualTo(newContent);
        verify(inputStreamProvider)
                .open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScript_shouldHandleMultipleFilesWithDifferentContent() {
        PythonScript script1 = PythonScript.asFile("script1", "script1.py");
        PythonScript script2 = PythonScript.asFile("script2", "script2.py");
        String content1 = "content1";
        String content2 = "content2";

        when(inputStreamProvider.open("script1.py"))
                .thenReturn(new ByteArrayInputStream(content1.getBytes(StandardCharsets.UTF_8)));
        when(inputStreamProvider.open("script2.py"))
                .thenReturn(new ByteArrayInputStream(content2.getBytes(StandardCharsets.UTF_8)));

        PythonScript result1 = fileReader.readScript(script1);
        PythonScript result2 = fileReader.readScript(script2);

        assertThat(result1.toPythonString().trim())
                .isEqualTo(content1);
        assertThat(result2.toPythonString().trim())
                .isEqualTo(content2);
        verify(inputStreamProvider)
                .open("script1.py");
        verify(inputStreamProvider)
                .open("script2.py");
    }
}
