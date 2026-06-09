package io.jpyxie.python.library;

import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.executor.PythonResultSpec;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;
import io.jpyxie.python.script.PythonScriptFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public boolean exists(PythonLibrary library) {
        try {
            String name = library.getName();
            log.debug("Checking if library [{}] exists", name);
            String scriptString = EXISTS_SCRIPT_STRING.formatted(name);
            PythonScript script = PythonScriptFactory.fromCharSequence("exists_" + name, scriptString);
            PythonResultMap resultMap = pythonExecutor.execute(script, EXISTS_ARGUMENT_SPEC);
            boolean exists = resultMap.get(EXISTS, Boolean.class);
            log.debug(exists ? "Library [{}] exists" : "Library [{}] does not exist", name);
            return exists;
        } catch (Exception e) {
            throw SubprocessPythonLibraryManagerException.failedToCheckExistence(library, e);
        }
    }

    @Override
    public void install(PythonLibrary library) {
        try {
            String name = library.getName();
            List<String> options = library.getOptions();
            log.info("Installing Python library [{}] with options [{}]", name, options);
            String joinedOptions = this.joinOptions(options);
            String scriptString = INSTALL_SCRIPT_STRING.formatted(name, joinedOptions);
            PythonScript script = PythonScriptFactory.fromCharSequence("install_" + name, scriptString);
            pythonExecutor.execute(script, PythonResultSpec.empty());
        } catch (Exception e) {
            throw SubprocessPythonLibraryManagerException.failedToInstall(library, e);
        }
    }

    @Override
    public void uninstall(PythonLibrary library) {
        try {
            String name = library.getName();
            List<String> options = library.getOptions();
            log.info("Uninstalling Python library [{}] with options [{}]", name, options);
            String joinedOptions = this.joinOptions(options);
            String scriptString = UNINSTALL_SCRIPT_STRING.formatted(name, joinedOptions);
            PythonScript script = PythonScriptFactory.fromCharSequence("uninstall_" + name, scriptString);
            pythonExecutor.execute(script, PythonResultSpec.empty());
        } catch (Exception e) {
            throw SubprocessPythonLibraryManagerException.failedToUninstall(library, e);
        }
    }

    private String joinOptions(@Nullable List<String> options) {
        return Optional.ofNullable(options)
                .stream()
                .map(option -> ", " + "\"" + option + "\"")
                .collect(Collectors.joining());
    }
}
