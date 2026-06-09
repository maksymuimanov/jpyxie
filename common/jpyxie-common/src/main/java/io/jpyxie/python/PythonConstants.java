package io.jpyxie.python;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PythonConstants {
    public static final String PYTHON = "python";
    public static final String PY = "py";
    public static final String FILE_FORMAT = ".py";

    public static final String IMPORT_REGEX = "(^import [\\w.]+$)|(^import [\\w.]+ as [\\w.]+$)|(^from [\\w.]+ import [\\w., ]+$)";
    public static final String IMPORT_JSON = "import json";

    public static final String M = "-m";
}
