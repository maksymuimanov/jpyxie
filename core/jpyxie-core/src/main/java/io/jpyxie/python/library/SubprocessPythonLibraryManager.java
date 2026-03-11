package io.jpyxie.python.library;

import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.executor.PythonResultSpec;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SubprocessPythonLibraryManager implements PythonLibraryManager {
    private static final String EXISTS_SCRIPT_STRING = """
            import subprocess, sys
            result = subprocess.run(
                [sys.executable, "-m", "pip", "show", "%s"],
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE
            )
            exists = result.returncode == 0
            """;
    private static final String EXISTS = "exists";
    private static final PythonResultSpec EXISTS_ARGUMENT_SPEC = PythonResultSpec.of(EXISTS, Boolean.class);
    private static final String INSTALL_SCRIPT_STRING = """
            import subprocess, sys
            cmd = [sys.executable, "-m", "pip", "install", "%s"%s]
            subprocess.check_call(cmd)
            """;
    private static final String UNINSTALL_SCRIPT_STRING = """
            import subprocess, sys
            cmd = [sys.executable, "-m", "pip", "uninstall", "--yes", "%s"%s]
            subprocess.check_call(cmd)
            """;
    private final PythonExecutor pythonExecutor;

    @Override
    public boolean exists(PythonLibrary management) {
        String name = management.getName();
        log.debug("Checking if library [{}] exists", name);
        String scriptString = EXISTS_SCRIPT_STRING.formatted(name);
        PythonScript script = PythonScript.asString("exists_" + name, scriptString);
        PythonResultMap resultMap = pythonExecutor.execute(script, EXISTS_ARGUMENT_SPEC);
        boolean exists = resultMap.get(EXISTS, Boolean.class);
        log.debug(exists ? "Library [{}] exists" : "Library [{}] does not exist", name);
        return exists;
    }

    @Override
    public void install(PythonLibrary management) {
        String name = management.getName();
        List<String> options = management.getOptions();
        log.info("Installing Python library [{}] with options [{}]", name, options);
        String joinedOptions = this.joinOptions(options);
        String scriptString = INSTALL_SCRIPT_STRING.formatted(name, joinedOptions);
        PythonScript script = PythonScript.asString("install_" + name, scriptString);
        pythonExecutor.execute(script, PythonResultSpec.empty());
    }

    @Override
    public void uninstall(PythonLibrary management) {
        String name = management.getName();
        List<String> options = management.getOptions();
        log.info("Uninstalling Python library [{}] with options [{}]", name, options);
        String joinedOptions = this.joinOptions(options);
        String scriptString = UNINSTALL_SCRIPT_STRING.formatted(name, joinedOptions);
        PythonScript script = PythonScript.asString("uninstall_" + name, scriptString);
        pythonExecutor.execute(script, PythonResultSpec.empty());
    }

    private String joinOptions(@Nullable List<String> options) {
        StringBuilder optionsStringBuilder = new StringBuilder();
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                String option = options.get(i);
                optionsStringBuilder.append(", ")
                        .append("\"")
                        .append(option)
                        .append("\"");
            }
        }
        return optionsStringBuilder.toString();
    }
}
