package io.w4t3rcs.python.constant;

import io.w4t3rcs.python.annotation.PythonBefore;
import io.w4t3rcs.python.annotation.PythonBefores;
import io.w4t3rcs.python.annotation.PythonParam;
import io.w4t3rcs.python.dto.PythonExecutionResponse;
import io.w4t3rcs.python.file.BasicPythonFileHandler;
import io.w4t3rcs.python.file.PythonFileHandler;
import io.w4t3rcs.python.properties.PythonFileProperties;
import org.springframework.core.env.Profiles;

import java.lang.reflect.Method;
import java.util.Map;

public final class TestConstants {
    //Script constants
    public static final String OK = "OK";
    public static final PythonExecutionResponse<String> OK_RESPONSE = new PythonExecutionResponse<>(OK);
    public static final Class<? extends String> STRING_CLASS = String.class;
    public static final Class<? extends PythonExecutionResponse<String>> STRING_RESPONSE_CLASS = (Class<? extends PythonExecutionResponse<String>>) OK_RESPONSE.getClass();
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
    public static final String FILE_READ_SCRIPT = "test_read.py";
    public static final String FILE_WRITE_SCRIPT = "test_write.py";

    //File constants
    public static final PythonFileProperties FILE_PROPERTIES = new PythonFileProperties("/");
    public static final PythonFileHandler FILE_HANDLER = new BasicPythonFileHandler(FILE_PROPERTIES);

    //Aspect constants
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
