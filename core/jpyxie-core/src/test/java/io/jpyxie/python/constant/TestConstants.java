package io.jpyxie.python.constant;

import io.jpyxie.python.processor.PythonResult;

import java.util.Map;

public final class TestConstants {
    public static final String OK = "OK";
    public static final PythonResult<String> OK_RESPONSE = new PythonResult<>(OK);
    public static final Class<String> STRING_CLASS = String.class;
    public static final Map<String, Object> EMPTY_ARGUMENTS = Map.of();
    public static final String SIMPLE_SCRIPT_0 = "print(2 + 2)";
    public static final String SIMPLE_SCRIPT_1 = "test_var1 = 2 + 2\ntest_var2 = 6 + 2\nprint(test_var1 + test_var2)";
    public static final String SIMPLE_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)";
    public static final String SIMPLE_SCRIPT_3 = "import json\ntest_var = 2 + 2\nprint('r4java' + json.dumps(test_var))";
    public static final String RESULT_SCRIPT_0 = "test_var = 2 + 2\no4java{test_var}";
    public static final String RESULT_SCRIPT_1 = "import json\ntest_var = 2 + 2\no4java{test_var}";
    public static final String RESULT_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)\no4java{test_var}";
    public static final String RESULT_SCRIPT_3 = "test_var = {'x': 2, 'y': 7}\nprint(test_var)\no4java{test_var}";
    public static final String SPELYTHON_SCRIPT_0 = "print(spel{#a})";
    public static final String SPELYTHON_SCRIPT_1 = "test_var1 = spel{#a}\ntest_var2 = spel{#b}\nprint(test_var1 + test_var2)";
    public static final String COMPOUND_SCRIPT_0 = "test_var = 'hello world'\nprint(test_var + spel{#a})\no4java{test_var}";
    public static final String COMPOUND_SCRIPT_1 = "import json\ntest_var = {'x': 2, 'y': spel{#b}}\nprint(test_var)\no4java{test_var}";
    public static final String FILE_SCRIPT = "test.py";
}
