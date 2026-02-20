package io.jpyxie.python.processor;

import io.jpyxie.python.executor.PythonResultRequirement;
import io.jpyxie.python.executor.PythonResultSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PythonResultMapTest {
    private PythonResultRequirement<String> requirement1;
    private PythonResultRequirement<Integer> requirement2;
    @Mock
    private Function<PythonResultRequirement<?>, Object> valueFunction;

    @BeforeEach
    void setUp() {
        requirement1 = spy(new PythonResultRequirement<>("result1", String.class));
        requirement2 = spy(new PythonResultRequirement<>("result2", Integer.class));
    }

    @Test
    void create_shouldReturnEmptyResultMap() {
        PythonResultMap resultMap = PythonResultMap.create();

        assertThat(resultMap.isEmpty())
                .isTrue();
        assertThat(resultMap.size())
                .isZero();
    }

    @Test
    void empty_shouldReturnEmptyResultMap() {
        PythonResultMap resultMap = PythonResultMap.empty();

        assertThat(resultMap.isEmpty())
                .isTrue();
        assertThat(resultMap.size())
                .isZero();
    }

    @Test
    void of_withResultSpecAndValueFunction_shouldCreateResultMap_whenSpecNotEmpty() {
        PythonResultSpec resultSpec = PythonResultSpec.create()
                .require(requirement1)
                .require(requirement2);

        when(valueFunction.apply(requirement1))
                .thenReturn("value1");
        when(valueFunction.apply(requirement2))
                .thenReturn(42);

        PythonResultMap resultMap = PythonResultMap.of(resultSpec, valueFunction);

        assertThat(resultMap.isEmpty())
                .isFalse();
        assertThat(resultMap.size())
                .isEqualTo(2);
        assertThat(resultMap.contains("result1"))
                .isTrue();
        assertThat(resultMap.contains("result2"))
                .isTrue();
        assertThat(resultMap.get("result1")
                .getBody())
                .isEqualTo("value1");
        assertThat(resultMap.get("result2")
                .getBody())
                .isEqualTo(42);
    }

    @Test
    void of_withResultSpecAndValueFunction_shouldReturnEmpty_whenSpecIsEmpty() {
        PythonResultSpec resultSpec = PythonResultSpec.empty();

        PythonResultMap resultMap = PythonResultMap.of(resultSpec, valueFunction);

        assertThat(resultMap.isEmpty())
                .isTrue();
        assertThat(resultMap)
                .isEqualTo(PythonResultMap.empty());
        verify(valueFunction, never())
                .apply(any());
    }

    @Test
    void of_withObjectsMap_shouldCreateResultMap_whenMapNotEmpty() {
        Map<String, Object> objects = Map.of(
                "key1", "value1",
                "key2", 42
        );

        PythonResultMap resultMap = PythonResultMap.of(objects);

        assertThat(resultMap.isEmpty())
                .isFalse();
        assertThat(resultMap.size())
                .isEqualTo(2);
        assertThat(resultMap.contains("key1"))
                .isTrue();
        assertThat(resultMap.contains("key2"))
                .isTrue();
        assertThat(resultMap.get("key1").getBody())
                .isEqualTo("value1");
        assertThat(resultMap.get("key2").getBody())
                .isEqualTo(42);
    }

    @Test
    void of_withObjectsMap_shouldReturnEmpty_whenMapIsEmpty() {
        Map<String, Object> emptyMap = Map.of();

        PythonResultMap resultMap = PythonResultMap.of(emptyMap);

        assertThat(resultMap.isEmpty())
                .isTrue();
        assertThat(resultMap)
                .isEqualTo(PythonResultMap.empty());
    }

    @Test
    void putObject_shouldAddResultToMap() {
        PythonResultMap resultMap = PythonResultMap.create();

        resultMap.putObject("testKey", "testValue");

        assertThat(resultMap.isEmpty())
                .isFalse();
        assertThat(resultMap.contains("testKey"))
                .isTrue();
        assertThat(resultMap.get("testKey").getBody())
                .isEqualTo("testValue");
    }

    @Test
    void putObject_shouldHandleNullValue() {
        PythonResultMap resultMap = PythonResultMap.create();

        resultMap.putObject("nullKey", null);

        assertThat(resultMap.contains("nullKey"))
                .isTrue();
        assertThat(resultMap.get("nullKey"))
                .isEqualTo(PythonResult.absent("nullKey"));
    }

    @Test
    void put_shouldAddResultToMap() {
        PythonResultMap resultMap = PythonResultMap.create();
        PythonResult<String> result = PythonResult.present("key", "value");

        resultMap.put("key", result);

        assertThat(resultMap.contains("key"))
                .isTrue();
        assertThat(resultMap.get("key"))
                .isSameAs(result);
    }

    @Test
    void put_shouldReplaceExistingResult() {
        PythonResultMap resultMap = PythonResultMap.create();
        PythonResult<String> originalResult = PythonResult.present("key", "original");
        PythonResult<String> newResult = PythonResult.present("key", "new");

        resultMap.put("key", originalResult);
        resultMap.put("key", newResult);

        assertThat(resultMap.size())
                .isEqualTo(1);
        assertThat(resultMap.get("key"))
                .isSameAs(newResult);
    }

    @Test
    void get_shouldReturnNullForNonExistentKey() {
        PythonResultMap resultMap = PythonResultMap.create();

        PythonResult<?> result = resultMap.get("nonExistent");

        assertThat(result)
                .isNull();
    }

    @Test
    void contains_shouldReturnFalseForNonExistentKey() {
        PythonResultMap resultMap = PythonResultMap.create();

        boolean contains = resultMap.contains("nonExistent");

        assertThat(contains)
                .isFalse();
    }

    @Test
    void keys_shouldReturnUnmodifiableSet() {
        PythonResultMap resultMap = PythonResultMap.create();
        resultMap.putObject("key1", "value1");
        resultMap.putObject("key2", "value2");

        Set<String> keys = resultMap.keys();

        assertThat(keys)
                .containsExactlyInAnyOrder("key1", "key2");
        assertThatThrownBy(() -> keys.add("key3"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void values_shouldReturnUnmodifiableSet() {
        PythonResultMap resultMap = PythonResultMap.create();
        PythonResult<String> result1 = PythonResult.present("key1", "value1");
        PythonResult<Integer> result2 = PythonResult.present("key2", 42);
        resultMap.put("key1", result1);
        resultMap.put("key2", result2);

        Set<PythonResult<?>> values = resultMap.values();

        assertThat(values)
                .containsExactlyInAnyOrder(result1, result2);
        assertThatThrownBy(() -> values.add(PythonResult.absent("key3")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void entries_shouldReturnUnmodifiableSet() {
        PythonResultMap resultMap = PythonResultMap.create();
        PythonResult<String> result = PythonResult.present("key", "value");
        resultMap.put("key", result);

        Set<Map.Entry<String, PythonResult<?>>> entries = resultMap.entries();

        assertThat(entries)
                .hasSize(1);
        Map.Entry<String, PythonResult<?>> entry = entries.iterator().next();
        assertThat(entry.getKey())
                .isEqualTo("key");
        assertThat(entry.getValue())
                .isSameAs(result);
        assertThatThrownBy(() -> entries.remove(entry))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void toMap_shouldReturnUnmodifiableMap() {
        PythonResultMap resultMap = PythonResultMap.create();
        resultMap.putObject("key", "value");

        Map<String, PythonResult<?>> map = resultMap.toMap();

        assertThat(map)
                .hasSize(1);
        assertThatThrownBy(() -> map.put("newKey", PythonResult.absent("newKey")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void size_shouldReturnCorrectSize() {
        PythonResultMap resultMap = PythonResultMap.create();

        assertThat(resultMap.size())
                .isZero();

        resultMap.putObject("key1", "value1");
        assertThat(resultMap.size())
                .isEqualTo(1);

        resultMap.putObject("key2", "value2");
        assertThat(resultMap.size())
                .isEqualTo(2);
    }

    @Test
    void isEmpty_shouldReturnCorrectStatus() {
        PythonResultMap resultMap = PythonResultMap.create();

        assertThat(resultMap.isEmpty())
                .isTrue();

        resultMap.putObject("key", "value");
        assertThat(resultMap.isEmpty())
                .isFalse();
    }

    @Test
    void constructor_shouldCreateCopyOfProvidedMap() {
        Map<String, Object> originalMap = Map.of(
                "key1", "value1",
                "key2", 42
        );

        PythonResultMap resultMap = PythonResultMap.of(originalMap);

        assertThat(resultMap.size())
                .isEqualTo(2);
        assertThat(resultMap.contains("key1"))
                .isTrue();
        assertThat(resultMap.contains("key2"))
                .isTrue();
    }
}
