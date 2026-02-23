package io.jpyxie.python.file;

import io.jpyxie.python.autoconfigure.PythonFileProperties;
import io.jpyxie.python.exception.PythonFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class ClassPathResourcePythonFileInputStreamProvider implements PythonFileInputStreamProvider {
    private final PythonFileProperties fileProperties;
    private final Environment environment;

    @Override
    public InputStream open(CharSequence path) {
        if (path.isEmpty()) throw new PythonFileException("Path cannot be empty");
        try {
            log.debug("Opening file: [{}]", path);
            String parentPath = fileProperties.getPath();
            ClassPathResource resource = new ClassPathResource(parentPath + path);
            if (resource.exists()) {
                log.debug("File found: [{}]", path);
                return resource.getInputStream();
            } else {
                log.debug("File not found: [{}], trying to seek in profile packages", path);
                String[] activeProfiles = environment.getActiveProfiles();
                for (String activeProfile : activeProfiles) {
                    ClassPathResource profileResource = new ClassPathResource(parentPath + activeProfile + "/" + path);
                    if (profileResource.exists()) {
                        log.debug("File found in profile package: [{}: {}]", activeProfile, path);
                        return profileResource.getInputStream();
                    }
                }
                throw new PythonFileException(path + " not found");
            }
        } catch (IOException e) {
            log.error("Failed to open file: [{}]", path, e);
            throw new PythonFileException(e);
        }
    }
}
