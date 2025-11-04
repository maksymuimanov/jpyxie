package io.maksymuimanov.python.schema;

import io.maksymuimanov.python.bind.PythonClass;
import io.maksymuimanov.python.bind.PythonInitFunction;
import io.maksymuimanov.python.bind.PythonMethodParameter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class BasicPythonSchemaMapper implements PythonSchemaMapper {
    public static final String JAVA_PACKAGE = "java";
    private final Map<Class<?>, PythonSchemaTypeConverter<PythonMethodParameter>> fieldTypeConverters;

    @Override
    @SneakyThrows
    public PythonSchema map(Class<?> clazz, PythonSchema schema, Set<Class<?>> mapped) {
        if (mapped.contains(clazz)) return schema;
        mapped.add(clazz);
        String className = clazz.getSimpleName();
        List<PythonMethodParameter> pythonMethodParameters = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers())) continue;
            Class<?> type = field.getType();
            String name = field.getName();
            PythonMethodParameter pythonMethodParameter = new PythonMethodParameter(name);
            pythonMethodParameters.add(pythonMethodParameter);
            for (var entry : fieldTypeConverters.entrySet()) {
                if (entry.getKey().isAssignableFrom(type)) {
                    entry.getValue().convert(pythonMethodParameter, type);
                    break;
                }
            }

            if ((Serializable.class.isAssignableFrom(type) && !type.getPackageName().startsWith(JAVA_PACKAGE))
                    || !fieldTypeConverters.containsKey(type)) {
                this.map(type, schema, mapped);
            }
        }

        PythonInitFunction initFunction = new PythonInitFunction(pythonMethodParameters);
        PythonClass pythonClass = new PythonClass(className, initFunction);
        schema.addClass(pythonClass);
        return schema;
    }
}
