package io.w4t3rcs.python.constant;

import io.w4t3rcs.python.properties.RestrictedPythonResolverProperties;
import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.RestrictedPythonResolver;

public final class TestConstants {
    //Script constants
    public static final String SIMPLE_SCRIPT_0 = "print(2 + 2)\nr4java = 2";
    public static final String SIMPLE_SCRIPT_1 = "test_var1 = 2 + 2\ntest_var2 = 6 + 2\nprint(test_var1 + test_var2)\nr4java = 2";
    public static final String SIMPLE_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)\nr4java = 2";
    public static final String SIMPLE_SCRIPT_3 = "import json\ntest_var = 2 + 2\nprint('r4java' + json.dumps(test_var))\nr4java = 2";
    public static final String RESULT_SCRIPT_0 = "test_var = 2 + 2\nr4java = 2";
    public static final String RESULT_SCRIPT_1 = "import json\ntest_var = 2 + 2\nr4java = 2";
    public static final String RESULT_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)\nr4java = 2";
    public static final String RESULT_SCRIPT_3 = "test_var = {'x': 2, 'y': 7}\nprint(test_var)\nr4java = 2";

    //Resolver constants
    public static final RestrictedPythonResolverProperties RESTRICTED_PYTHON_PROPERTIES = new RestrictedPythonResolverProperties("from RestrictedPython import compile_restricted\nfrom RestrictedPython import safe_globals", "source_code", "execution_result", "r4java", true);
    public static final PythonResolver RESTRICTED_PYTHON_RESOLVER = new RestrictedPythonResolver(RESTRICTED_PYTHON_PROPERTIES);
}
