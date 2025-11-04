package io.maksymuimanov.python;

import io.maksymuimanov.python.bind.PythonMethodParameter;
import io.maksymuimanov.python.schema.*;
import io.maksymuimanov.python.script.PythonScript;
import io.maksymuimanov.python.script.PythonScriptBuilder;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TestTest {
    @Test
    void test() {
        List<PythonSchemaTypeConverter<PythonMethodParameter>> converters = List.of(
                new PythonSchemaIntFieldTypeConverter(),
                new PythonSchemaFloatFieldTypeConverter(),
                new PythonSchemaStringFieldTypeConverter(),
                new PythonSchemaBooleanFieldTypeConverter(),
                new PythonSchemaSerializableFieldTypeConverter()
        );
        Map<Class<?>, PythonSchemaTypeConverter<PythonMethodParameter>> converterMap = new HashMap<>();
        for (PythonSchemaTypeConverter<PythonMethodParameter> converter : converters) {
            for (Class<?> supportedClass : converter.getSupportedClasses()) {
                converterMap.put(supportedClass, converter);
            }
        }

        PythonSchemaMapper classSerializer = new BasicPythonSchemaMapper(converterMap);
        PythonSchema parsed = classSerializer.map(A.class, new PythonSchema(), new HashSet<>());


        PythonScript pythonScript = new PythonScript("print(2 + 2)\na = 3\nprint(a)\n");
        PythonScriptBuilder builder = pythonScript.getBuilder();
        builder.mergeToStart(new PythonScript(parsed.toPythonString()));
        System.out.println(pythonScript);
    }

    public static class A implements Serializable {
        String a;
        B b;
        C c;
        A ameba;
    }

    public static class B implements Serializable {
        int x = 125;
        byte y = -1-5;
        double u = -15.e3;
        transient boolean z = true;
        char t = 't';
    }

    public static class C implements Serializable {
        D d;
        E e;
    }

    public static class D implements Serializable {
        boolean z = true;
    }

    public static class E implements Serializable {
        char t = 't';
    }
}
