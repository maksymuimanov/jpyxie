package io.maksymuimanov.python.constant;

public final class TestConstants {
    public static final String RESULT_SCRIPT_0 = "test_var = 2 + 2\no4java{test_var}";
    public static final String RESULT_SCRIPT_1 = "import json\ntest_var = 2 + 2\no4java{test_var}";
    public static final String RESULT_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)\no4java{test_var}";
    public static final String RESULT_SCRIPT_3 = "test_var = {'x': 2, 'y': 7}\nprint(test_var)\no4java{test_var}";
    public static final String REGEX = "o4java\\{.+?}";
    public static final String APPEARANCE = "r4java";
    public static final int POSITION_FROM_START = 7;
    public static final int POSITION_FROM_END = 1;
    public static final boolean PRINTED = true;
}
