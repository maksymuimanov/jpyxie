package io.jpyxie.python.script;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class MergingPythonScriptBuilderTest {
    private MergingPythonScriptBuilder builder;
    private PythonScript targetScript;
    private PythonScript sourceScript;

    @BeforeEach
    void setUp() {
        targetScript = spy(PythonScript.asString("target", "import json\nprint(':D')"));
        sourceScript = spy(PythonScript.asString("source", "import os\nprint('hello')"));
        builder = MergingPythonScriptBuilder.of(targetScript);
    }

    @Test
    void of_shouldCreateBuilderWithScriptAndBasicBuilder() {
        MergingPythonScriptBuilder result = MergingPythonScriptBuilder.of(targetScript);

        assertThat(result)
                .isNotNull();
        assertThat(result.getScript())
                .isSameAs(targetScript);
    }

    @Test
    void mergeToStart_shouldPrependImportsAndCode() {
        MergingPythonScriptBuilder result = builder.mergeToStart(sourceScript);

        assertThat(result)
                .isSameAs(builder);
        assertThat(targetScript.importLines())
                .containsExactly(new PythonImportLine("import os"), new PythonImportLine("import json"));
        assertThat(targetScript.codeLines())
                .containsExactly(new PythonCodeLine("print('hello')"), new PythonCodeLine("print(':D')"));
    }

    @Test
    void merge_shouldAppendImportsAndCode() {
        MergingPythonScriptBuilder result = builder.merge(sourceScript);

        assertThat(result)
                .isSameAs(builder);
        assertThat(targetScript.importLines())
                .containsExactly(new PythonImportLine("import json"), new PythonImportLine("import os"));
        assertThat(targetScript.codeLines())
                .containsExactly(new PythonCodeLine("print(':D')"), new PythonCodeLine("print('hello')"));
    }
}
