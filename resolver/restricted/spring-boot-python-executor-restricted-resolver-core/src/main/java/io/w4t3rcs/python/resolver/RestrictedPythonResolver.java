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
        List<String> importNames = script.getAllImportNames();
        String codeVariableName = resolverProperties.codeVariableName();
        String localVariablesName = resolverProperties.localVariablesName();
        String resultAppearance = resolverProperties.resultAppearance();
        script.prependCode(codeVariableName, " = \"\"\"");
        script.appendCode("\"\"\"");
        script.appendCode(localVariablesName, " = {}");
        script.appendCode("restricted_byte_code = compile_restricted(", codeVariableName, ", '<inline>', 'exec')");
        script.appendCode("exec(restricted_byte_code, safe_globals_with_imports, ", localVariablesName, ")");
        for (String importName : importNames) {
            script.prependCode("safe_globals_with_imports['", importName, "'] = ", importName);
        }
        boolean printEnabled = resolverProperties.printEnabled();
        String safeGlobalsVariable = "safe_globals_with_imports = dict(safe_globals)";
        script.prependCode(safeGlobalsVariable);
        script.appendImport(resolverProperties.importLine());
        if (script.containsCode(resultAppearance)) {
            script.appendCode(resultAppearance, " = execution_result['", resultAppearance, "']");
        }
        if (printEnabled) {
            script.prependCode("_getattr_ = getattr");
            script.prependCode("_print_ = PrintCollector");
            script.appendImport("from RestrictedPython.PrintCollector import PrintCollector");
            int index = script.getCodeIndex(safeGlobalsVariable) + 1;
            script.insertCode("safe_globals_with_imports['_getattr_'] = _getattr_", index);
            script.insertCode("safe_globals_with_imports['_print_'] = _print_", index);
        }
        return script;
    }
}
