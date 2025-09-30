package io.w4t3rcs.python.constant;

import io.w4t3rcs.python.properties.Py4JResolverProperties;
import io.w4t3rcs.python.resolver.Py4JResolver;
import io.w4t3rcs.python.resolver.PythonResolver;

public final class TestConstants {
    public static final String SIMPLE_SCRIPT_0 = "print(2 + 2)";
    public static final String SIMPLE_SCRIPT_1 = "test_var1 = 2 + 2\ntest_var2 = 6 + 2\nprint(test_var1 + test_var2)";
    public static final String SIMPLE_SCRIPT_2 = "test_var = 'hello world'\nprint(test_var)";
    public static final String SIMPLE_SCRIPT_3 = "import json\ntest_var = 2 + 2\nprint('r4java' + json.dumps(test_var))";

    //Resolver constants
    public static final Py4JResolverProperties PY4J_RESOLVER_PROPERTIES = new Py4JResolverProperties("(^import [\\w.]+$)|(^import [\\w.]+ as [\\w.]+$)|(^from [\\w.]+ import [\\w., ]+$)", "from py4j.java_gateway import JavaGateway, GatewayParameters", "gateway = JavaGateway(\n\tgateway_parameters=GatewayParameters(\n\t\t%s\n\t)\n)", new String[]{"address=\"localhost\""});
    public static final PythonResolver PY4J_RESOLVER = new Py4JResolver(PY4J_RESOLVER_PROPERTIES);
}
