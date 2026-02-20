package io.jpyxie.python.script;

import io.jpyxie.python.constant.PythonConstants;
import io.jpyxie.python.exception.PythonScriptException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PythonScriptTest {
    @Test
    void parse_shouldCreateFileScript_whenSourceEndsWithPy() {
        PythonScript script = PythonScript.parse("test", "test.py");

        assertThat(script.name())
                .isEqualTo("test");
        assertThat(script.source())
                .isEqualTo("test.py");
        assertThat(script.isFile())
                .isTrue();
        assertThat(script.isImportEmpty())
                .isTrue();
        assertThat(script.isCodeEmpty())
                .isTrue();
    }

    @Test
    void parse_shouldCreateStringScript_whenSourceDoesNotEndWithPy() {
        String scriptContent = "print('hello')";

        PythonScript script = PythonScript.parse("test", scriptContent);

        assertThat(script.name())
                .isEqualTo("test");
        assertThat(script.source())
                .isEqualTo(scriptContent);
        assertThat(script.isFile())
                .isFalse();
        assertThat(script.isImportEmpty())
                .isTrue();
        assertThat(script.isCodeEmpty())
                .isFalse();
    }

    @Test
    void asFile_withNameOnly_shouldAddPyExtension_whenMissing() {
        PythonScript script = PythonScript.asFile("test");

        assertThat(script.name())
                .isEqualTo("test");
        assertThat(script.source())
                .isEqualTo("test.py");
        assertThat(script.isFile())
                .isTrue();
    }

    @Test
    void asFile_withNameOnly_shouldKeepPyExtension_whenPresent() {
        PythonScript script = PythonScript.asFile("test.py");

        assertThat(script.name())
                .isEqualTo("test.py");
        assertThat(script.source())
                .isEqualTo("test.py");
        assertThat(script.isFile())
                .isTrue();
    }

    @Test
    void asFile_withNameAndScript_shouldCreateFileScript_whenScriptEndsWithPy() {
        String scriptContent = "test.py";

        PythonScript script = PythonScript.asFile("test", scriptContent);

        assertThat(script.name())
                .isEqualTo("test");
        assertThat(script.source())
                .isEqualTo(scriptContent);
        assertThat(script.isFile())
                .isTrue();
    }

    @Test
    void asFile_withNameAndScript_shouldThrowException_whenScriptDoesNotEndWithPy() {
        String scriptContent = "print('hello')";

        assertThatThrownBy(() -> PythonScript.asFile("test", scriptContent))
                .isInstanceOf(PythonScriptException.class)
                .hasMessage("Invalid file name format. It must end with " + PythonConstants.FILE_FORMAT);
    }

    @Test
    void isFile_shouldReturnTrue_whenSourceEndsWithPy() {
        assertThat(PythonScript.isFile("test.py"))
                .isTrue();
        assertThat(PythonScript.isFile("script.py"))
                .isTrue();
        assertThat(PythonScript.isFile("path/to/test.py"))
                .isTrue();
    }

    @Test
    void isFile_shouldReturnFalse_whenSourceDoesNotEndWithPy() {
        assertThat(PythonScript.isFile("test"))
                .isFalse();
        assertThat(PythonScript.isFile("test.txt"))
                .isFalse();
        assertThat(PythonScript.isFile("test.pyc"))
                .isFalse();
    }

    @Test
    void asString_shouldCreateStringScript() {
        String scriptContent = "import os\nprint('hello')";

        PythonScript script = PythonScript.asString("test", scriptContent);

        assertThat(script.name())
                .isEqualTo("test");
        assertThat(script.source())
                .isEqualTo(scriptContent);
        assertThat(script.isFile())
                .isFalse();
        assertThat(script.isImportEmpty())
                .isFalse();
        assertThat(script.isCodeEmpty())
                .isFalse();
    }

    @Test
    void empty_shouldCreateEmptyScript() {
        PythonScript script = PythonScript.empty("test");

        assertThat(script.name())
                .isEqualTo("test");
        assertThat(script.source())
                .isEqualTo("");
        assertThat(script.isFile())
                .isFalse();
        assertThat(script.isImportEmpty())
                .isTrue();
        assertThat(script.isCodeEmpty())
                .isTrue();
    }

    @Test
    void getImportsSize_shouldReturnNumberOfImports() {
        PythonScript script = PythonScript.asString("test", "import os\nimport sys\nprint('hello')");

        assertThat(script.getImportsSize())
                .isEqualTo(2);
    }

    @Test
    void getCodeSize_shouldReturnNumberOfCodeLines() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')\nprint('world')");

        assertThat(script.getCodeSize())
                .isEqualTo(2);
    }

    @Test
    void containsImport_shouldReturnTrue_whenImportExists() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')");

        assertThat(script.containsImport("import os"))
                .isTrue();
        assertThat(script.containsImport("import sys"))
                .isFalse();
    }

    @Test
    void containsImport_withImportLine_shouldReturnTrueWhenImportExists() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')");
        PythonImportLine existingImport = new PythonImportLine("import os");
        PythonImportLine nonExistingImport = new PythonImportLine("import sys");

        assertThat(script.containsImport(existingImport))
                .isTrue();
        assertThat(script.containsImport(nonExistingImport))
                .isFalse();
    }

    @Test
    void containsDeepImport_shouldReturnTrue_whenImportContainsSubstring() {
        PythonScript script = PythonScript.asString("test", "import os.path\nprint('hello')");

        assertThat(script.containsDeepImport("os"))
                .isTrue();
        assertThat(script.containsDeepImport("path"))
                .isTrue();
        assertThat(script.containsDeepImport("sys"))
                .isFalse();
    }

    @Test
    void containsCode_shouldReturnTrue_whenCodeExists() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')");

        assertThat(script.containsCode("print('hello')"))
                .isTrue();
        assertThat(script.containsCode("print('world')"))
                .isFalse();
    }

    @Test
    void containsCode_withCodeLine_shouldReturnTrueWhenCodeExists() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')");
        String existingCode = "print('hello')";
        String nonExistingCode = "print('world')";

        assertThat(script.containsCode(existingCode))
                .isTrue();
        assertThat(script.containsCode(nonExistingCode))
                .isFalse();
    }

    @Test
    void containsDeepCode_shouldReturnTrue_whenCodeContainsSubstring() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello world')");

        assertThat(script.containsDeepCode("hello"))
                .isTrue();
        assertThat(script.containsDeepCode("world"))
                .isTrue();
        assertThat(script.containsDeepCode("python"))
                .isFalse();
    }

    @Test
    void startsWithCode_shouldReturnTrue_whenCodeStartsWithGivenLine() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')");

        assertThat(script.startsWithCode("print('hello')"))
                .isTrue();
        assertThat(script.startsWithCode("import os"))
                .isFalse();
    }

    @Test
    void startsWithCode_withCodeLine_shouldReturnTrueWhenCodeStartsWithGivenLine() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')");
        PythonCodeLine firstCode = new PythonCodeLine("print('hello')");
        PythonCodeLine notFirstCode = new PythonCodeLine("print('world')");

        assertThat(script.startsWithCode(firstCode))
                .isTrue();
        assertThat(script.startsWithCode(notFirstCode))
                .isFalse();
    }

    @Test
    void endsWithCode_shouldReturnTrue_whenCodeEndsWithGivenLine() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')\nprint('world')");

        assertThat(script.endsWithCode("print('world')"))
                .isTrue();
        assertThat(script.endsWithCode("print('hello')"))
                .isFalse();
    }

    @Test
    void endsWithCode_withCodeLine_shouldReturnTrueWhenCodeEndsWithGivenLine() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')\nprint('world')");
        PythonCodeLine lastCode = new PythonCodeLine("print('world')");
        PythonCodeLine notLastCode = new PythonCodeLine("print('hello')");

        assertThat(script.endsWithCode(lastCode))
                .isTrue();
        assertThat(script.endsWithCode(notLastCode))
                .isFalse();
    }

    @Test
    void isImportEmpty_shouldReturnTrue_whenNoImports() {
        PythonScript script = PythonScript.asString("test", "print('hello')");

        assertThat(script.isImportEmpty())
                .isTrue();
    }

    @Test
    void isImportEmpty_shouldReturnFalse_whenHasImports() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')");

        assertThat(script.isImportEmpty())
                .isFalse();
    }

    @Test
    void isCodeEmpty_shouldReturnTrue_whenNoCode() {
        PythonScript script = PythonScript.asString("test", "import os");

        assertThat(script.isCodeEmpty())
                .isTrue();
    }

    @Test
    void isCodeEmpty_shouldReturnFalse_whenHasCode() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')");

        assertThat(script.isCodeEmpty())
                .isFalse();
    }

    @Test
    void getImport_shouldReturnImportAtIndex() {
        PythonScript script = PythonScript.asString("test", "import os\nimport sys\nprint('hello')");

        PythonImportLine firstImport = script.getImport(0);
        PythonImportLine secondImport = script.getImport(1);

        assertThat(firstImport.toPythonString())
                .isEqualTo("import os");
        assertThat(secondImport.toPythonString())
                .isEqualTo("import sys");
    }

    @Test
    void getCode_shouldReturnCodeAtIndex() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')\nprint('world')");

        PythonCodeLine firstCode = script.getCode(0);
        PythonCodeLine secondCode = script.getCode(1);

        assertThat(firstCode.toPythonString())
                .isEqualTo("print('hello')");
        assertThat(secondCode.toPythonString())
                .isEqualTo("print('world')");
    }

    @Test
    void getName_shouldReturnScriptName() {
        PythonScript script = PythonScript.asString("test", "print('hello')");

        assertThat(script.name())
                .isEqualTo("test");
    }

    @Test
    void getSource_shouldReturnScriptSource() {
        String source = "print('hello')";
        PythonScript script = PythonScript.asString("test", source);

        assertThat(script.source())
                .isEqualTo(source);
    }

    @Test
    void isFile_shouldReturnFileStatus() {
        PythonScript fileScript = PythonScript.asFile("test.py");
        PythonScript stringScript = PythonScript.asString("test", "print('hello')");

        assertThat(fileScript.isFile())
                .isTrue();
        assertThat(stringScript.isFile())
                .isFalse();
    }

    @Test
    void toPythonString_shouldGenerateCorrectPythonCode() {
        PythonScript script = PythonScript.asString("test", "import os\nprint('hello')\nprint('world')");

        String pythonString = script.toPythonString();

        assertThat(pythonString)
                .isEqualTo("import os\nprint('hello')\nprint('world')\n");
    }

    @Test
    void toPythonString_shouldReturnEqualResult() {
        PythonScript script = PythonScript.asString("test", "print('hello')");

        String firstCall = script.toPythonString();
        String secondCall = script.toPythonString();

        assertThat(firstCall)
                .isEqualTo(secondCall);
    }

    @Test
    void toString_shouldReturnPythonString() {
        PythonScript script = PythonScript.asString("test", "print('hello')");

        assertThat(script.toString())
                .isEqualTo(script.toPythonString());
    }

    @Test
    void equals_shouldReturnTrueForIdenticalScripts() {
        PythonScript script1 = PythonScript.asString("test", "print('hello')");
        PythonScript script2 = PythonScript.asString("test", "print('hello')");

        assertThat(script1)
                .isEqualTo(script2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentNames() {
        PythonScript script1 = PythonScript.asString("test1", "print('hello')");
        PythonScript script2 = PythonScript.asString("test2", "print('hello')");

        assertThat(script1)
                .isNotEqualTo(script2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentSources() {
        PythonScript script1 = PythonScript.asString("test", "print('hello')");
        PythonScript script2 = PythonScript.asString("test", "print('world')");

        assertThat(script1)
                .isNotEqualTo(script2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentFileStatus() {
        PythonScript fileScript = PythonScript.asFile("test.py");
        PythonScript stringScript = PythonScript.asString("test.py", "print('hello')");

        assertThat(fileScript)
                .isNotEqualTo(stringScript);
    }

    @Test
    void hashCode_shouldBeSameForEqualScripts() {
        PythonScript script1 = PythonScript.asString("test", "print('hello')");
        PythonScript script2 = PythonScript.asString("test", "print('hello')");

        assertThat(script1.hashCode())
                .isEqualTo(script2.hashCode());
    }

    @Test
    void hashCode_shouldBeDifferentForDifferentScripts() {
        PythonScript script1 = PythonScript.asString("test1", "print('hello')");
        PythonScript script2 = PythonScript.asString("test2", "print('hello')");

        assertThat(script1.hashCode())
                .isNotEqualTo(script2.hashCode());
    }

    @Test
    void constructor_shouldCreateScriptWithAllParameters() {
        List<PythonImportLine> imports = List.of(new PythonImportLine("import os"));
        List<PythonCodeLine> codes = List.of(new PythonCodeLine("print('hello')"));

        PythonScript script = new PythonScript("test", "source", true, imports, codes);

        assertThat(script.name())
                .isEqualTo("test");
        assertThat(script.source())
                .isEqualTo("source");
        assertThat(script.isFile())
                .isTrue();
        assertThat(script.importLines())
                .isEqualTo(imports);
        assertThat(script.codeLines())
                .isEqualTo(codes);
    }
}
