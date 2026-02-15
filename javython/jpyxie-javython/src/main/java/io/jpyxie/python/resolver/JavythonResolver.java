package io.jpyxie.python.resolver;

import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.exception.PythonScriptException;
import io.jpyxie.python.script.BasicPythonScriptBuilder;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.script.PythonScript;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JavythonResolver implements PythonResolver {
    public static final String DEFAULT_REGEX = "java\\{.+?}";
    public static final int DEFAULT_POSITION_FROM_START = 5;
    public static final int DEFAULT_POSITION_FROM_END = 1;
    private final PythonSerializer pythonSerializer;
    private final String regex;
    private final int positionFromStart;
    private final int positionFromEnd;

    public JavythonResolver(PythonSerializer pythonSerializer) {
        this(pythonSerializer, DEFAULT_REGEX, DEFAULT_POSITION_FROM_START, DEFAULT_POSITION_FROM_END);
    }

    @Override
    public PythonScript resolve(PythonScript pythonScript, PythonArgumentSpec argumentSpec) {
        return BasicPythonScriptBuilder.of(pythonScript)
                .appendImport(IMPORT_JSON)
                .replaceAllCode(this.regex,
                        this.positionFromStart,
                        this.positionFromEnd,
                        (group, result) -> {
                            try {
                                Object argument = argumentSpec.get(group);
                                PythonRepresentation pythonRepresentation = pythonSerializer.serialize(argument);
                                result.append(pythonRepresentation.toPythonString());
                            } catch (Exception e) {
                                throw new PythonScriptException(e);
                            }
                        })
                .getScript();
    }
}
