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

import static org.junit.jupiter.api.Assertions.*;
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
        testScript = PythonScript.fromFile("test_script", "test_script.py");
    }

    @Test
    void readScriptShouldReturnScriptAsIsWhenNotFile() {
        PythonScript nonFileScript = PythonScript.fromString("test_script", "print('Hello World')");

        PythonScript result = fileReader.readScript(nonFileScript);

        assertSame(result, nonFileScript);
        verifyNoInteractions(inputStreamProvider);
    }

    @Test
    @SuppressWarnings("resource")
    void readScriptShouldLoadFileContentWhenFile() {
        String expectedContent = "print('Hello World')";
        InputStream mockInputStream = new ByteArrayInputStream(expectedContent.getBytes(StandardCharsets.UTF_8));

        when(inputStreamProvider.open("test_script.py")).thenReturn(mockInputStream);

        PythonScript result = fileReader.readScript(testScript);

        assertEquals("test_script.py", result.getSource());
        assertEquals(expectedContent, result.toPythonString().trim());
        verify(inputStreamProvider).open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScriptShouldCacheFileContent() {
        String expectedContent = "print('cached content')";
        InputStream mockInputStream = new ByteArrayInputStream(expectedContent.getBytes(StandardCharsets.UTF_8));
        PythonScript script1 = PythonScript.fromFile("script1", "test_script.py");
        PythonScript script2 = PythonScript.fromFile("script2", "test_script.py");

        when(inputStreamProvider.open("test_script.py")).thenReturn(mockInputStream);

        PythonScript result1 = fileReader.readScript(script1);
        PythonScript result2 = fileReader.readScript(script2);

        assertEquals(expectedContent, result1.toPythonString().trim());
        assertEquals(expectedContent, result2.toPythonString().trim());
        verify(inputStreamProvider, times(1)).open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScriptShouldThrowPythonFileExceptionWhenInputStreamFails() {
        when(inputStreamProvider.open("test_script.py")).thenThrow(RuntimeException.class);

        assertThrows(PythonFileException.class, () -> fileReader.readScript(testScript));
        verify(inputStreamProvider).open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScriptShouldThrowPythonFileExceptionWhenReadingBytesFails() throws IOException {
        InputStream mockInputStream = mock(InputStream.class);

        when(inputStreamProvider.open("test_script.py")).thenReturn(mockInputStream);
        when(mockInputStream.readAllBytes()).thenThrow(RuntimeException.class);

        assertThrows(PythonFileException.class, () -> fileReader.readScript(testScript));
        verify(inputStreamProvider).open("test_script.py");
        verify(mockInputStream).close();
    }

    @Test
    @SuppressWarnings("resource")
    void readScriptShouldHandleEmptyFile() {
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);

        when(inputStreamProvider.open("test_script.py")).thenReturn(emptyStream);

        PythonScript result = fileReader.readScript(testScript);

        assertTrue(result.toPythonString().isEmpty());
        verify(inputStreamProvider).open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScriptShouldAppendContentToExistingScript() {
        String newContent = "new line";
        InputStream mockInputStream = new ByteArrayInputStream(newContent.getBytes(StandardCharsets.UTF_8));
        PythonScript scriptWithContent = PythonScript.fromFile("test_script", "test_script.py");

        when(inputStreamProvider.open("test_script.py")).thenReturn(mockInputStream);

        PythonScript result = fileReader.readScript(scriptWithContent);

        assertEquals(newContent, result.toPythonString().trim());
        verify(inputStreamProvider).open("test_script.py");
    }

    @Test
    @SuppressWarnings("resource")
    void readScriptShouldHandleMultipleFilesWithDifferentContent() {
        PythonScript script1 = PythonScript.fromFile("script1", "script1.py");
        PythonScript script2 = PythonScript.fromFile("script2", "script2.py");
        String content1 = "content1";
        String content2 = "content2";

        when(inputStreamProvider.open("script1.py"))
                .thenReturn(new ByteArrayInputStream(content1.getBytes(StandardCharsets.UTF_8)));
        when(inputStreamProvider.open("script2.py"))
                .thenReturn(new ByteArrayInputStream(content2.getBytes(StandardCharsets.UTF_8)));

        PythonScript result1 = fileReader.readScript(script1);
        PythonScript result2 = fileReader.readScript(script2);

        assertEquals(content1, result1.toPythonString().trim());
        assertEquals(content2, result2.toPythonString().trim());
        verify(inputStreamProvider).open("script1.py");
        verify(inputStreamProvider).open("script2.py");
    }
}
