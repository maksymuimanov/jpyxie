package io.maksymuimanov.python.constant;

import io.maksymuimanov.python.annotation.PythonBefore;
import io.maksymuimanov.python.annotation.PythonBefores;
import io.maksymuimanov.python.annotation.PythonParam;
import org.springframework.core.env.Profiles;

import java.lang.reflect.Method;
import java.util.Map;

public final class TestConstants {
    public static final Map<String, Object> EMPTY_ARGUMENTS = Map.of();
    public static final String SIMPLE_SCRIPT_0 = "print(2 + 2)";
    public static final String SIMPLE_SCRIPT_1 = "test_var1 = 2 + 2\ntest_var2 = 6 + 2\nprint(test_var1 + test_var2)";
    public static final String SIMPLE_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)";
    public static final String TEST_PROFILE = "test";
    public static final String[] TEST_PROFILES = new String[]{TEST_PROFILE};
    public static final Profiles PROFILES_OBJECT = Profiles.of(TEST_PROFILES);
    public static final String[] EMPTY_PROFILES = {};
    public static final String A_PYTHON_PARAM = "a";
    public static final String CUSTOM_PYTHON_PARAM = "custom";
    public static Method DUMMY_METHOD;

    static {
        try {
            DUMMY_METHOD = TestConstants.class.getDeclaredMethod("doDummy", String.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    @PythonBefore(script = SIMPLE_SCRIPT_0, activeProfiles = {TEST_PROFILE})
    @PythonBefores(value = {
            @PythonBefore(script = SIMPLE_SCRIPT_0, activeProfiles = {TEST_PROFILE}),
            @PythonBefore(script = SIMPLE_SCRIPT_1),
            @PythonBefore(script = SIMPLE_SCRIPT_2)
    })
    private static void doDummy(String a, @PythonParam(CUSTOM_PYTHON_PARAM) String custom) {
        //Just a stub method for mocking in JoinPoint object during tests
    }
}
