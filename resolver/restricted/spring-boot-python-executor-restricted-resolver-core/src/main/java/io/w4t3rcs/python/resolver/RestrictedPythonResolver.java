package io.w4t3rcs.python.resolver;

import io.w4t3rcs.python.properties.RestrictedPythonResolverProperties;
import io.w4t3rcs.python.script.PythonScript;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * {@link PythonResolver} implementation that processes Python scripts for secure execution
 * using the {@code RestrictedPython} library.
 *
 * <p>This resolver acts as a proxy, injecting necessary setup code to enable safe execution of Python code with RestrictedPython.
 * It performs the following operations:
 * <ul>
 *   <li>Removes existing import lines matching a configured regex and collects import names.</li>
 *   <li>Wraps the original script code in a string variable for compilation.</li>
 *   <li>Initializes local variables container for execution results.</li>
 *   <li>Compiles the wrapped script with RestrictedPython's compile_restricted method.</li>
 *   <li>Executes the compiled code in a safe globals context augmented with collected imports.</li>
 *   <li>Replaces configured body fragments with JSON serialization code if enabled.</li>
 *   <li>Inserts necessary import statements and setup for safe globals and optional print support.</li>
 * </ul>
 *
 * @see PythonResolver
 * @see PythonResolverHolder
 * @see RestrictedPythonResolverProperties
 * @see <a href="https://github.com/zopefoundation/RestrictedPython">RestrictedPython</a>
 * @author w4t3rcs
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class RestrictedPythonResolver implements PythonResolver {
    private final RestrictedPythonResolverProperties resolverProperties;

    /**
     * Resolves the given Python script by injecting RestrictedPython setup code to
     * enable secure execution.
     *
     * @param script the original Python script content (non-null)
     * @param arguments unused map of variables for script execution context, may be null
     * @return the transformed Python script ready for execution with RestrictedPython
     */
    @Override
    public PythonScript resolve(PythonScript script, Map<String, Object> arguments) {
        String codeVariableName = resolverProperties.codeVariableName();
        String localVariablesName = resolverProperties.localVariablesName();
        List<String> importNames = script.getAllImportNames();
        String safeGlobalsVariable = "safe_globals_with_imports = dict(safe_globals)";
        String resultAppearance = resolverProperties.resultAppearance();
        String importLine = resolverProperties.importLine();
        boolean printEnabled = resolverProperties.printEnabled();
        return script.prependCode(codeVariableName, " = \"\"\"")
                .appendCode("\"\"\"")
                .appendCode(localVariablesName, " = {}")
                .appendCode("restricted_byte_code = compile_restricted(", codeVariableName, ", '<inline>', 'exec')")
                .appendCode("exec(restricted_byte_code, safe_globals_with_imports, ", localVariablesName, ")")
                .iterate(importNames, importName -> {
                    script.prependCode("safe_globals_with_imports['", importName, "'] = ", importName);
                })
                .prependCode(safeGlobalsVariable)
                .appendImport(importLine)
                .perform(() -> {
                    script.appendCode(resultAppearance, " = execution_result['", resultAppearance, "']");
                }, script.containsCode(resultAppearance))
                .perform(() -> {
                    script.prependCode("_getattr_ = getattr")
                            .prependCode("_print_ = PrintCollector")
                            .appendImport("from RestrictedPython.PrintCollector import PrintCollector");
                    int index = script.getCodeIndex(safeGlobalsVariable) + 1;
                    script.insertCode("safe_globals_with_imports['_getattr_'] = _getattr_", index)
                            .insertCode("safe_globals_with_imports['_print_'] = _print_", index);
                }, printEnabled);
    }
}
