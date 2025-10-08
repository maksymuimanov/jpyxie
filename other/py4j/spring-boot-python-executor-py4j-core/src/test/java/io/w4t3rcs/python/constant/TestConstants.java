package io.w4t3rcs.python.constant;

public final class TestConstants {
    public static final String SIMPLE_SCRIPT_0 = "print(2 + 2)";
    public static final String SIMPLE_SCRIPT_1 = "test_var1 = 2 + 2\ntest_var2 = 6 + 2\nprint(test_var1 + test_var2)";
    public static final String SIMPLE_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)";
    public static final String SIMPLE_SCRIPT_3 = "import json\ntest_var = 2 + 2\nprint('r4java' + json.dumps(test_var))";
    public static final String PY4J_IMPORT_LINE= "from py4j.java_gateway import JavaGateway, GatewayParameters";
    public static final String PY4J_GATEWAY_OBJECT = "gateway = JavaGateway(\n\tgateway_parameters=GatewayParameters(\n\t\t%s\n\t)\n)";
    public static final String[] PY4J_GATEWAY_PROPERTIES = {"address=\"localhost\""};
}
