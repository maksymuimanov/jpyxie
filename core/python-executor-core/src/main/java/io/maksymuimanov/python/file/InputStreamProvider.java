package io.maksymuimanov.python.file;

import java.io.InputStream;

@FunctionalInterface
public interface InputStreamProvider {
    InputStream open(String path);
}
