package io.maksymuimanov.python.file;

import io.maksymuimanov.python.autoconfigure.PythonFileProperties;
import io.maksymuimanov.python.exception.PythonFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class ClassPathResourceInputStreamProvider implements InputStreamProvider {
    private final PythonFileProperties fileProperties;
    private final Environment environment;

    @Override
    public InputStream open(CharSequence path) {
        try {
            log.debug("Opening file: [{}]", path);
            ClassPathResource resource = new ClassPathResource(fileProperties.getPath() + path);
            if (resource.exists()) {
                log.debug("File found: [{}]", path);
                return resource.getInputStream();
            } else {
                log.debug("File not found: [{}], trying to seek in profile packages", path);
                String[] activeProfiles = environment.getActiveProfiles();
                for (String activeProfile : activeProfiles) {
                    ClassPathResource profileResource = new ClassPathResource(fileProperties.getPath() + activeProfile + "/" + path);
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
