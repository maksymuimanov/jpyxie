package io.w4t3rcs.python.constant;

import io.w4t3rcs.python.properties.SpelythonResolverProperties;

public final class TestConstants {
    //Script constants
    public static final String SPELYTHON_SCRIPT_0 = "print(spel{#a})";
    public static final String SPELYTHON_SCRIPT_1 = "test_var1 = spel{#a}\ntest_var2 = spel{#b}\nprint(test_var1 + test_var2)";

    //Resolver constants
    public static final SpelythonResolverProperties SPELYTHON_PROPERTIES = new SpelythonResolverProperties("spel\\{.+?}", "#", 5, 1);
}
