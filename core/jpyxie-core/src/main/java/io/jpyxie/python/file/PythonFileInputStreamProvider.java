package io.jpyxie.python.file;

import java.io.InputStream;

@FunctionalInterface
public interface PythonFileInputStreamProvider {
    String DEFAULT_PARENT_DIRECTORY = "/python/";

    InputStream open(CharSequence path);
}
