package io.jpyxie.python.resolver;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PythonArgumentSpecTest {
    @Test
    void empty_shouldReturnEmptySpec() {
        PythonArgumentSpec spec = PythonArgumentSpec.empty();

        assertThat(spec.toMap())
                .isEmpty();
    }

    @Test
    void empty_shouldReturnImmutableInstance() {
        PythonArgumentSpec empty1 = PythonArgumentSpec.empty();
        PythonArgumentSpec empty2 = PythonArgumentSpec.empty();

        assertThat(empty1)
                .isEqualTo(empty2);
    }

    @Test
    void create_shouldReturnEmptySpec() {
        PythonArgumentSpec spec = PythonArgumentSpec.create();

        assertThat(spec.toMap())
                .isEmpty();
    }

    @Test
    void create_shouldReturnNewInstance() {
        PythonArgumentSpec spec1 = PythonArgumentSpec.create();
        PythonArgumentSpec spec2 = PythonArgumentSpec.create();

        assertThat(spec1)
                .isNotSameAs(spec2);
        assertThat(spec1)
                .isEqualTo(spec2);
    }

    @Test
    void of_withNameAndValue_shouldCreateSpecWithSingleArgument() {
        PythonArgumentSpec spec = PythonArgumentSpec.of("arg1", "value1");

        assertThat(spec.toMap())
                .hasSize(1);
        assertThat(spec.get("arg1"))
                .isEqualTo("value1");
    }

    @Test
    void of_withNameAndValueAndOthers_shouldCreateSpecWithMultipleArguments() {
        PythonArgumentSpec spec = PythonArgumentSpec.of("arg1", "value1", "arg2", 42, "arg3", true);

        assertThat(spec.toMap())
                .hasSize(3);
        assertThat(spec.get("arg1"))
                .isEqualTo("value1");
        assertThat(spec.get("arg2"))
                .isEqualTo(42);
        assertThat(spec.get("arg3"))
                .isEqualTo(true);
    }

    @Test
    void of_withNameAndValueAndOthers_shouldHandleOddNumberOfArguments() {
        assertThatThrownBy(() -> PythonArgumentSpec.of("arg1", "value1", "arg2"))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    void of_withMap_shouldCreateSpecWithAllMapEntries() {
        Map<String, Object> arguments = Map.of(
                "arg1", "value1",
                "arg2", 42,
                "arg3", true
        );

        PythonArgumentSpec spec = PythonArgumentSpec.of(arguments);

        assertThat(spec.toMap())
                .hasSize(3);
        assertThat(spec.get("arg1"))
                .isEqualTo("value1");
        assertThat(spec.get("arg2"))
                .isEqualTo(42);
        assertThat(spec.get("arg3"))
                .isEqualTo(true);
    }

    @Test
    void of_withMap_shouldHandleEmptyMap() {
        Map<String, Object> emptyMap = Map.of();

        PythonArgumentSpec spec = PythonArgumentSpec.of(emptyMap);

        assertThat(spec.toMap())
                .isEmpty();
    }

    @Test
    void with_shouldAddArgumentAndReturnSameInstance() {
        PythonArgumentSpec spec = PythonArgumentSpec.create();

        PythonArgumentSpec result = spec.with("arg1", "value1");

        assertThat(result)
                .isSameAs(spec);
        assertThat(spec.get("arg1"))
                .isEqualTo("value1");
    }

    @Test
    void with_shouldSupportChaining() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("arg1", "value1")
                .with("arg2", 42)
                .with("arg3", true);

        assertThat(spec.toMap())
                .hasSize(3);
        assertThat(spec.get("arg1"))
                .isEqualTo("value1");
        assertThat(spec.get("arg2"))
                .isEqualTo(42);
        assertThat(spec.get("arg3"))
                .isEqualTo(true);
    }

    @Test
    void with_shouldReplaceExistingArgument() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("arg1", "original")
                .with("arg1", "replaced");

        assertThat(spec.toMap())
                .hasSize(1);
        assertThat(spec.get("arg1"))
                .isEqualTo("replaced");
    }

    @Test
    void putAll_shouldAddAllArgumentsFromMap() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("existing", "value");

        Map<String, Object> newArguments = Map.of(
                "arg1", "value1",
                "arg2", 42
        );

        PythonArgumentSpec result = spec.putAll(newArguments);

        assertThat(result)
                .isSameAs(spec);
        assertThat(spec.toMap())
                .hasSize(3);
        assertThat(spec.get("existing"))
                .isEqualTo("value");
        assertThat(spec.get("arg1"))
                .isEqualTo("value1");
        assertThat(spec.get("arg2"))
                .isEqualTo(42);
    }

    @Test
    void putAll_shouldHandleEmptyMap() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("arg1", "value1");

        Map<String, Object> emptyMap = Map.of();

        PythonArgumentSpec result = spec.putAll(emptyMap);

        assertThat(result)
                .isSameAs(spec);
        assertThat(spec.toMap())
                .hasSize(1);
    }

    @Test
    void putAll_shouldReplaceExistingArguments() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("arg1", "original")
                .with("arg2", "original");

        Map<String, Object> newArguments = Map.of(
                "arg1", "replaced",
                "arg3", "new"
        );

        spec.putAll(newArguments);

        assertThat(spec.toMap())
                .hasSize(3);
        assertThat(spec.get("arg1"))
                .isEqualTo("replaced");
        assertThat(spec.get("arg2"))
                .isEqualTo("original");
        assertThat(spec.get("arg3"))
                .isEqualTo("new");
    }

    @Test
    void get_shouldReturnValue_whenArgumentExists() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("arg1", "value1");

        Object value = spec.get("arg1");

        assertThat(value)
                .isEqualTo("value1");
    }

    @Test
    void get_shouldReturnNull_whenArgumentDoesNotExist() {
        PythonArgumentSpec spec = PythonArgumentSpec.create();

        Object value = spec.get("nonExistent");

        assertThat(value)
                .isNull();
    }

    @Test
    void toMap_shouldReturnUnmodifiableMap() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("arg1", "value1");

        Map<String, Object> map = spec.toMap();

        assertThatThrownBy(() -> map.put("arg2", "value2"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void toMap_shouldReturnAllArguments() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("arg1", "value1")
                .with("arg2", 42)
                .with("arg3", true);

        Map<String, Object> map = spec.toMap();

        assertThat(map)
                .hasSize(3);
        assertThat(map)
                .containsEntry("arg1", "value1")
                .containsEntry("arg2", 42)
                .containsEntry("arg3", true);
    }

    @Test
    void toString_shouldContainArguments() {
        PythonArgumentSpec spec = PythonArgumentSpec.create()
                .with("arg1", "value1");

        String stringRepresentation = spec.toString();

        assertThat(stringRepresentation)
                .contains("PythonArgumentSpec")
                .contains("arguments=");
    }

    @Test
    void constructor_shouldCreateSpecWithProvidedArguments() {
        Map<String, Object> arguments = Map.of(
                "arg1", "value1",
                "arg2", 42
        );

        PythonArgumentSpec spec = PythonArgumentSpec.of(arguments);

        assertThat(spec.toMap())
                .isEqualTo(arguments);
    }

    @Test
    void of_withVarArgs_shouldHandleDifferentTypes() {
        PythonArgumentSpec spec = PythonArgumentSpec.of(
                "string", "value",
                "integer", 42,
                "double", 3.14,
                "boolean", true,
                "object", new Object()
        );

        assertThat(spec.toMap())
                .hasSize(5);
        assertThat(spec.get("string"))
                .isEqualTo("value");
        assertThat(spec.get("integer"))
                .isEqualTo(42);
        assertThat(spec.get("double"))
                .isEqualTo(3.14);
        assertThat(spec.get("boolean"))
                .isEqualTo(true);
        assertThat(spec.get("object"))
                .isInstanceOf(Object.class);
    }
}
