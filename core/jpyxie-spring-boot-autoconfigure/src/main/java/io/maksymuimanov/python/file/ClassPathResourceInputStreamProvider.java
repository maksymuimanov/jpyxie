package io.maksymuimanov.python.file;

import io.maksymuimanov.python.autoconfigure.PythonFileProperties;
import io.maksymuimanov.python.exception.PythonFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class ClassPathResourceInputStreamProvider implements InputStreamProvider {
    private final PythonFileProperties fileProperties;
    private final Environment environment;

    @Override
    public InputStream open(CharSequence path) {
        try {
            ClassPathResource resource = new ClassPathResource(fileProperties.getPath() + path);
            if (resource.exists()) {
                return resource.getInputStream();
            } else {
                String[] activeProfiles = environment.getActiveProfiles();
                for (String activeProfile : activeProfiles) {
                    ClassPathResource profileResource = new ClassPathResource(fileProperties.getPath() + activeProfile + "/" + path);
                    if (profileResource.exists()) {
                        return profileResource.getInputStream();
                    }
                }
                throw new PythonFileException(path + " not found");
            }
        } catch (IOException e) {
            throw new PythonFileException(e);
        }
    }
}
