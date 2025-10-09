package io.maksymuimanov.python.constant;

public final class TestConstants {
    public static final String SPELYTHON_SCRIPT_0 = "print(spel{#a})";
    public static final String SPELYTHON_SCRIPT_1 = "test_var1 = spel{#a}\ntest_var2 = spel{#b}\nprint(test_var1 + test_var2)";
    public static final String REGEX = "spel\\{.+?}";
    public static final String LOCAL_VARIABLE_INDEX = "#";
    public static final int POSITION_FROM_START = 5;
    public static final int POSITION_FROM_END = 1;
}
