package io.jpyxie.python.script;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.MatchResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BasicPythonScriptBuilderTest {
    private BasicPythonScriptBuilder builder;
    private PythonScript script;

    @BeforeEach
    void setUp() {
        script = spy(PythonScript.asString("script", "import json\nprint(':D')"));
        builder = BasicPythonScriptBuilder.of(script);
    }

    @Test
    void of_shouldCreateBuilderWithScript() {
        BasicPythonScriptBuilder result = BasicPythonScriptBuilder.of(script);

        assertThat(result)
                .isNotNull();
        assertThat(result.getScript())
                .isSameAs(script);
    }

    @Test
    void appendAll_shouldAppendAllLines() {
        String scriptContent = "import os\nprint('hello')\nprint('world')";

        BasicPythonScriptBuilder result = builder.appendAll(scriptContent);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2);
        assertThat(script.codeLines())
                .hasSize(3);
    }

    @Test
    void append_shouldAppendImport_whenLineMatchesImportRegex() {
        String importStatement = "import os";

        BasicPythonScriptBuilder result = builder.append(importStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void append_shouldAppendCode_whenLineDoesNotMatchImportRegex() {
        String codeStatement = "print('hello')";

        BasicPythonScriptBuilder result = builder.append(codeStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void prepend_shouldPrependImport_whenLineMatchesImportRegex() {
        String importStatement = "import os";

        BasicPythonScriptBuilder result = builder.prepend(importStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void prepend_shouldPrependCode_whenLineDoesNotMatchImportRegex() {
        String codeStatement = "print('hello')";

        BasicPythonScriptBuilder result = builder.prepend(codeStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void insert_shouldInsertImport_whenLineMatchesImportRegex() {
        String importStatement = "import os";
        int index = 0;

        BasicPythonScriptBuilder result = builder.insert(importStatement, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void insert_shouldInsertCode_whenLineDoesNotMatchImportRegex() {
        String codeStatement = "print('hello')";
        int index = 0;

        BasicPythonScriptBuilder result = builder.insert(codeStatement, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void set_shouldSetImport_whenLineMatchesImportRegex() {
        String importStatement = "import os";
        int index = 0;

        BasicPythonScriptBuilder result = builder.set(importStatement, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(1)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void set_shouldSetCode_whenLineDoesNotMatchImportRegex() {
        String codeStatement = "print('hello')";
        int index = 0;

        BasicPythonScriptBuilder result = builder.set(codeStatement, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(1)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void remove_shouldRemoveImport_whenLineMatchesImportRegex() {
        String importStatement = "import os";

        BasicPythonScriptBuilder result = builder.remove(importStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(1)
                .doesNotContain(new PythonImportLine("import os"));
    }

    @Test
    void remove_shouldRemoveCode_whenLineDoesNotMatchImportRegex() {
        String codeStatement = "print('hello')";

        BasicPythonScriptBuilder result = builder.remove(codeStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(1)
                .doesNotContain(new PythonCodeLine("print('hello')"));
    }

    @Test
    void appendImport_withCharSequence_shouldCreateImportLineAndAppend() {
        String importStatement = "import os";

        BasicPythonScriptBuilder result = builder.appendImport(importStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void appendImport_withImportLine_shouldNotAddWhenAlreadyContains() {
        PythonImportLine importLine = new PythonImportLine("import json");

        BasicPythonScriptBuilder result = builder.appendImport(importLine);

        assertThat(result)
                .isSameAs(builder);
        assertThat(result.getScript()
                .importLines()
                .get(0))
                .isNotSameAs(importLine)
                .isEqualTo(importLine);
        verify(script)
                .containsImport(importLine);
    }

    @Test
    void appendImport_withImportLine_shouldAddWhenNotContains() {
        PythonImportLine importLine = new PythonImportLine("import sys");

        BasicPythonScriptBuilder result = builder.appendImport(importLine);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import sys"));
    }

    @Test
    void prependImport_withCharSequence_shouldCreateImportLineAndPrepend() {
        String importStatement = "import os";

        BasicPythonScriptBuilder result = builder.prependImport(importStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void prependImport_withImportLine_shouldNotAddWhenAlreadyContains() {
        PythonImportLine importLine = new PythonImportLine("import json");

        BasicPythonScriptBuilder result = builder.prependImport(importLine);

        assertThat(result)
                .isSameAs(builder);
        assertThat(result.getScript()
                .importLines()
                .get(0))
                .isNotSameAs(importLine)
                .isEqualTo(importLine);
        verify(script)
                .containsImport(importLine);
    }

    @Test
    void prependImport_withImportLine_shouldAddWhenNotContains() {
        PythonImportLine importLine = new PythonImportLine("import sys");

        BasicPythonScriptBuilder result = builder.prependImport(importLine);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import sys"));
    }

    @Test
    void insertImport_withCharSequence_shouldCreateImportLineAndInsert() {
        String importStatement = "import os";
        int index = 0;

        BasicPythonScriptBuilder result = builder.insertImport(importStatement, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void insertImport_withImportLine_shouldInsertAtSpecifiedIndex() {
        PythonImportLine importLine = new PythonImportLine("import os");
        int index = 1;

        BasicPythonScriptBuilder result = builder.insertImport(importLine, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(2)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void setImport_withCharSequence_shouldCreateImportLineAndSet() {
        String importStatement = "import os";
        int index = 0;

        BasicPythonScriptBuilder result = builder.setImport(importStatement, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(1)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void setImport_withImportLine_shouldSetAtSpecifiedIndex() {
        PythonImportLine importLine = new PythonImportLine("import os");
        int index = 0;

        BasicPythonScriptBuilder result = builder.setImport(importLine, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .hasSize(1)
                .contains(new PythonImportLine("import os"));
    }

    @Test
    void removeImport_withIndex_shouldRemoveImportAtSpecifiedIndex() {
        int index = 0;

        BasicPythonScriptBuilder result = builder.removeImport(index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .isEmpty();
    }

    @Test
    void removeImport_withCharSequence_shouldCreateImportLineAndRemove() {
        String importStatement = "import json";

        BasicPythonScriptBuilder result = builder.removeImport(importStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .isEmpty();
    }

    @Test
    void removeImport_withImportLine_shouldRemoveImport() {
        PythonImportLine importLine = new PythonImportLine("import json");

        BasicPythonScriptBuilder result = builder.removeImport(importLine);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.importLines())
                .isEmpty();
    }

    @Test
    void appendCode_withVarArgs_shouldConcatenateAndAppend() {
        String linePart1 = "print(";
        String linePart2 = "'hello'";
        String linePart3 = ")";

        BasicPythonScriptBuilder result = builder.appendCode(linePart1, linePart2, linePart3);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void appendCode_withCharSequence_shouldCreateCodeLineAndAppend() {
        String codeStatement = "print('hello')";

        BasicPythonScriptBuilder result = builder.appendCode(codeStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void appendCode_withCodeLine_shouldAppendCode() {
        PythonCodeLine codeLine = new PythonCodeLine("print('hello')");

        BasicPythonScriptBuilder result = builder.appendCode(codeLine);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void prependCode_withVarArgs_shouldConcatenateAndPrepend() {
        String linePart1 = "print(";
        String linePart2 = "'hello'";
        String linePart3 = ")";

        BasicPythonScriptBuilder result = builder.prependCode(linePart1, linePart2, linePart3);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void prependCode_withCharSequence_shouldCreateCodeLineAndPrepend() {
        String codeStatement = "print('hello')";

        BasicPythonScriptBuilder result = builder.prependCode(codeStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void prependCode_withCodeLine_shouldPrependCode() {
        PythonCodeLine codeLine = new PythonCodeLine("print('hello')");

        BasicPythonScriptBuilder result = builder.prependCode(codeLine);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void insertCode_withCharSequence_shouldCreateCodeLineAndInsert() {
        String codeStatement = "print('hello')";
        int index = 0;

        BasicPythonScriptBuilder result = builder.insertCode(codeStatement, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void insertCode_withCodeLine_shouldInsertAtSpecifiedIndex() {
        PythonCodeLine codeLine = new PythonCodeLine("print('hello')");
        int index = 1;

        BasicPythonScriptBuilder result = builder.insertCode(codeLine, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(2)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void setCode_withCharSequence_shouldCreateCodeLineAndSet() {
        String codeStatement = "print('hello')";
        int index = 0;

        BasicPythonScriptBuilder result = builder.setCode(codeStatement, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(1)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void setCode_withCodeLine_shouldSetAtSpecifiedIndex() {
        PythonCodeLine codeLine = new PythonCodeLine("print('hello')");
        int index = 0;

        BasicPythonScriptBuilder result = builder.setCode(codeLine, index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(1)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void removeCode_withIndex_shouldRemoveCodeAtSpecifiedIndex() {
        int index = 0;

        BasicPythonScriptBuilder result = builder.removeCode(index);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .isEmpty();
    }

    @Test
    void removeCode_withCharSequence_shouldCreateCodeLineAndRemove() {
        String codeStatement = "print(':D')";

        BasicPythonScriptBuilder result = builder.removeCode(codeStatement);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .isEmpty();
    }

    @Test
    void removeCode_withCodeLine_shouldRemoveCode() {
        PythonCodeLine codeLine = new PythonCodeLine("print(':D')");

        BasicPythonScriptBuilder result = builder.removeCode(codeLine);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .isEmpty();
    }

    @Test
    void replaceAllCode_withRegexAndFunction_shouldReplaceMatchingPatterns() {
        String regex = ":D";
        int start = 0;
        int end = 0;
        Function<String, String> function = s -> "hello";

        BasicPythonScriptBuilder result = builder.replaceAllCode(regex, start, end, function);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(1)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void replaceAllCode_withRegexAndBiConsumer_shouldReplaceMatchingPatterns() {
        String regex = ":D";
        int start = 0;
        int end = 0;
        BiConsumer<String, StringBuilder> function = (s, sb) -> sb.append("hello");

        BasicPythonScriptBuilder result = builder.replaceAllCode(regex, start, end, function);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(1)
                .contains(new PythonCodeLine("print('hello')"));
    }

    @Test
    void replaceAllCode_withMatchResultFunction_shouldReplaceMatchingPatterns() {
        String regex = ":D";
        Function<MatchResult, String> function = match -> "hello";

        BasicPythonScriptBuilder result = builder.replaceAllCode(regex, function);

        assertThat(result)
                .isSameAs(builder);
        assertThat(script.codeLines())
                .hasSize(1)
                .contains(new PythonCodeLine("print('hello')"));
    }
}
