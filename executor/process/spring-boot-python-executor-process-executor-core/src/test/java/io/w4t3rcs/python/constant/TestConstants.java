package io.w4t3rcs.python.constant;

public final class TestConstants {
    //Script constants
    public static final String OK = "OK";
    public static final Class<String> STRING_CLASS = String.class;
    public static final String SIMPLE_SCRIPT_0 = "print(2 + 2)";
    public static final String SIMPLE_SCRIPT_1 = "test_var1 = 2 + 2\ntest_var2 = 6 + 2\nprint(test_var1 + test_var2)";
    public static final String SIMPLE_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)";
    public static final String SIMPLE_SCRIPT_3 = "import json\ntest_var = 2 + 2\nprint('r4java' + json.dumps(test_var))";
    public static final String BAD_SCRIPT_0 = "print(2 + 2) print(2 - 2)";
    public static final String BAD_SCRIPT_1 = "print(2 + 2). print(2 - 2)";
    public static final String BAD_SCRIPT_2 = "import jsonnn\ntest_var = {'x': 2, 'y': 2}\nprint(test_var)";
    public static final String BAD_SCRIPT_3 = "import json\ntest_var = {'x': 2, 'y': 2}\nprint(test_varr)";
}
