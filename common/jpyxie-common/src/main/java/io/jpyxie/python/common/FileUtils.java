package io.jpyxie.python.common;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class FileUtils {
    private static final String PROJECT_DIR_PROPERTY = "user.dir";
    private static final String CURRENT_DIRECTORY = ".";

    public static Path getProjectDirectory() {
        String userDirectory = System.getProperty(PROJECT_DIR_PROPERTY);
        Path rootDirectory = Path.of(CURRENT_DIRECTORY)
                .normalize()
                .toAbsolutePath();
        if (rootDirectory.startsWith(userDirectory) ) {
            return rootDirectory;
        } else {
            throw PythonFileException.projectDirectoryIsNotUserDirectoryChild(userDirectory);
        }
    }
}
