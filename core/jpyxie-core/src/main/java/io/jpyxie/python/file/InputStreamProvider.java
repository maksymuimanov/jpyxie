package io.jpyxie.python.file;

import java.io.InputStream;

@FunctionalInterface
public interface InputStreamProvider {
    InputStream open(CharSequence path);
}
