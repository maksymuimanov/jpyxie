package io.w4t3rcs.python.constant;

import io.w4t3rcs.python.properties.ResultResolverProperties;
import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.ResultResolver;

public final class TestConstants {
    //Script constants
    public static final String RESULT_SCRIPT_0 = "test_var = 2 + 2\no4java{test_var}";
    public static final String RESULT_SCRIPT_1 = "import json\ntest_var = 2 + 2\no4java{test_var}";
    public static final String RESULT_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)\no4java{test_var}";
    public static final String RESULT_SCRIPT_3 = "test_var = {'x': 2, 'y': 7}\nprint(test_var)\no4java{test_var}";

    //Resolver constants
    public static final ResultResolverProperties RESULT_PROPERTIES = new ResultResolverProperties("o4java\\{.+?}", "r4java", 7, 1, true);
    public static final PythonResolver RESULT_RESOLVER = new ResultResolver(RESULT_PROPERTIES);
}
