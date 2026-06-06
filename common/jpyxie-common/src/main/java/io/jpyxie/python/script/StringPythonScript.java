package io.jpyxie.python.script;

import io.jpyxie.python.exception.PythonScriptException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class StringPythonScript extends PythonScript {
    public static StringPythonScript fromInputStream(String name, InputStream inputStream, Charset charset) {
        try {
            byte[] bytes = inputStream.readAllBytes();
            String scriptString = new String(bytes, charset);
            return fromCharSequence(name, scriptString);
        } catch (IOException e) {
            throw PythonScriptException.inputStreamException(e);
        }
    }

    public static StringPythonScript fromCharSequence(String name, CharSequence charSequence) {
        String scriptString = charSequence.toString();
        return (StringPythonScript) PythonScript.fromStream(name, scriptString.lines());
    }

    public StringPythonScript(String name) {
        super(name);
    }

    public StringPythonScript(String name, List<PythonScriptLine> importLines, List<PythonScriptLine> codeLines) {
        super(name, importLines, codeLines);
    }
}
