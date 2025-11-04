package io.maksymuimanov.python.schema;

import java.util.Set;

public interface PythonSchemaMapper {
    PythonSchema map(Class<?> clazz, PythonSchema schema, Set<Class<?>> mapped);
}
