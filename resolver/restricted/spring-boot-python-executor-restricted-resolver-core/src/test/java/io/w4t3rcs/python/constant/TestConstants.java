package io.w4t3rcs.python.constant;

public final class TestConstants {
    public static final String SIMPLE_SCRIPT_0 = "print(2 + 2)\nr4java = 2";
    public static final String SIMPLE_SCRIPT_1 = "test_var1 = 2 + 2\ntest_var2 = 6 + 2\nprint(test_var1 + test_var2)\nr4java = 2";
    public static final String SIMPLE_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)\nr4java = 2";
    public static final String SIMPLE_SCRIPT_3 = "import json\ntest_var = 2 + 2\nprint('r4java' + json.dumps(test_var))\nr4java = 2";
    public static final String RESULT_SCRIPT_0 = "test_var = 2 + 2\nr4java = 2";
    public static final String RESULT_SCRIPT_1 = "import json\ntest_var = 2 + 2\nr4java = 2";
    public static final String RESULT_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)\nr4java = 2";
    public static final String RESULT_SCRIPT_3 = "test_var = {'x': 2, 'y': 7}\nprint(test_var)\nr4java = 2";
    public static final String IMPORT_LINE = "from RestrictedPython import compile_restricted\nfrom RestrictedPython import safe_globals";
    public static final String CODE_VARIABLE_NAME = "source_code";
    public static final String LOCAL_VARIABLES_NAME = "execution_result";
    public static final String RESULT_APPEARANCE = "r4java";
    public static final boolean PRINTED = true;
}
