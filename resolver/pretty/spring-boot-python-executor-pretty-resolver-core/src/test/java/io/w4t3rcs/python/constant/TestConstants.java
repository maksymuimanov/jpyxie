package io.w4t3rcs.python.constant;

import io.w4t3rcs.python.resolver.PrettyResolver;
import io.w4t3rcs.python.resolver.PythonResolver;

public final class TestConstants {
    //Script constants
    public static final String SIMPLE_SCRIPT_0 = "\ntest_var = 2 + 2\n\n\n\no4java{test_var}";
    public static final String SIMPLE_SCRIPT_1 = "import json\n\n\ntest_var = 2 + 2\no4java{test_var}";
    public static final String SIMPLE_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)\no4java{test_var}";
    public static final String SIMPLE_SCRIPT_3 = "test_var = {'x': 2, 'y': 7}\n\n\n\nprint(test_var)\n\n\no4java{test_var}";

    //Resolver constants
    public static final PythonResolver PRETTY_RESOLVER = new PrettyResolver();
}
