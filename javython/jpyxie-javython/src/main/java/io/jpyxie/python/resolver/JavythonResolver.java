package io.jpyxie.python.resolver;

import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.script.PythonScript;
import io.jpyxie.python.script.PythonScriptBuilder;
import io.jpyxie.python.script.PythonScriptLine;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class JavythonResolver implements PythonResolver {
    public static final String DEFAULT_REGEX = "java\\{([^}]+)}";
    private final PythonSerializer pythonSerializer;
    private final Pattern pattern;

    public JavythonResolver(PythonSerializer pythonSerializer) {
        this(pythonSerializer, DEFAULT_REGEX);
    }

    public JavythonResolver(PythonSerializer pythonSerializer, String regex) {
        this(pythonSerializer, Pattern.compile(regex));
    }

    @Override
    public PythonScript resolve(PythonScript pythonScript, PythonArgumentSpec argumentSpec) {
        PythonScriptBuilder scriptBuilder = new PythonScriptBuilder(pythonScript);
        return scriptBuilder.mapAllCode(scriptLine -> {
            String line = scriptLine.getLine();
            String result = this.pattern.matcher(line).replaceAll(matchResult -> {
                String argumentName = matchResult.group(1);
                Object argument = argumentSpec.get(argumentName);
                return this.pythonSerializer.serialize(argument).toPythonString();
            });
            return new PythonScriptLine(result);
        }).getScript();
    }
}
