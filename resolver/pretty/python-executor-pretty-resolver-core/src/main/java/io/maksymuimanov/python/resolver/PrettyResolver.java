package io.maksymuimanov.python.resolver;

import io.maksymuimanov.python.script.PythonImportLine;
import io.maksymuimanov.python.script.PythonScript;
import io.maksymuimanov.python.script.PythonScriptBuilder;

import java.util.Map;

public class PrettyResolver implements PythonResolver {
    @Override
    public PythonScript resolve(PythonScript pythonScript, Map<String, Object> arguments) {
        String emptyLine = "";
        PythonScriptBuilder builder = pythonScript.getBuilder();
        return builder.doOnCondition(() -> builder.prependCode(emptyLine),
                        !pythonScript.startsWithCode(emptyLine))
                .doOnCondition(() -> builder.removeCode(PythonScript.START_INDEX),
                        pythonScript.isImportEmpty())
                .iterateImportLines((importLine,  index) -> {
                    if (!emptyLine.equals(importLine.getLine()) || index.equals(pythonScript.getImportsSize() - 1)) return;
                    int currentIndex = index;
                    while (true) {
                        currentIndex++;
                        PythonImportLine nextImportLine = pythonScript.getImport(currentIndex);
                        if (!importLine.equals(nextImportLine)) break;
                        builder.setImport((PythonImportLine) null, currentIndex);
                    }
                })
                .removeAllNullImports()
                .iterateCodeLines((codeLine,  index) -> {
                    if (!emptyLine.equals(codeLine) || index.equals(pythonScript.getCodeSize() - 1)) return;
                    int currentIndex = index;
                    while (true) {
                        currentIndex++;
                        String nextCodeLine = pythonScript.getCodeLine(currentIndex);
                        if (!codeLine.equals(nextCodeLine)) break;
                        builder.setCode(null, currentIndex);
                    }
                })
                .removeAllNullCode()
                .doOnCondition(() -> {
                    int lastElement = pythonScript.getCodeSize() - 1;
                    builder.removeCode(lastElement);
                }, pythonScript.endsWithCode(emptyLine))
                .build();
    }
}
