package io.jpyxie.python.script;

import io.jpyxie.python.autoconfigure.PythonFileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
public class SpringPythonScriptFactory {
    public static final String DEFAULT_PARENT_DIRECTORY = "/python/";
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private final PythonFileProperties fileProperties;
    private final Environment environment;

    public FilePythonScript fromResource(String name, CharSequence path) {
        if (path.isEmpty()) throw SpringPythonScriptFactoryException.emptyPath();
        try {
            log.debug("Opening file: [{}]", path);
            String parentPath = fileProperties.getPath();
            ClassPathResource resource = new ClassPathResource(parentPath + path);
            Charset charset = Charset.forName(fileProperties.getCharset());
            if (resource.exists()) {
                log.debug("File found: [{}]", path);
                return PythonScriptFactory.fromFile(name, resource.getFile(), charset);
            } else {
                log.debug("File not found: [{}], trying to seek in profile packages", path);
                String[] activeProfiles = environment.getActiveProfiles();
                for (String activeProfile : activeProfiles) {
                    ClassPathResource profileResource = new ClassPathResource(parentPath + activeProfile + "/" + path);
                    if (profileResource.exists()) {
                        log.debug("File found in profile package: [{}: {}]", activeProfile, path);
                        return PythonScriptFactory.fromFile(name, profileResource.getFile(), charset);
                    }
                }
                throw SpringPythonScriptFactoryException.notFound(path);
            }
        } catch (IOException e) {
            throw SpringPythonScriptFactoryException.failedToOpen(name, path, e);
        }
    }

}
