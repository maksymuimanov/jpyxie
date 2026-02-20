package io.jpyxie.python.executor;

import io.jpyxie.python.exception.PythonException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class PythonResultSpecTest {
    @Test
    void empty_shouldReturnEmptyUnmodifiableSpec() {
        PythonResultSpec spec = PythonResultSpec.empty();

        assertThat(spec.isEmpty())
                .isTrue();
        assertThat(spec.toMap())
                .isEmpty();
        assertThatThrownBy(() -> spec.require("result", String.class))
                .hasNoSuppressedExceptions();
    }

    @Test
    void create_shouldReturnEmptyModifiableSpec() {
        PythonResultSpec spec = PythonResultSpec.create();

        assertThat(spec.isEmpty())
                .isTrue();
        assertThat(spec.toMap())
                .isEmpty();
        assertThatCode(() -> spec.require("result", String.class))
                .doesNotThrowAnyException();
    }

    @Test
    void create_shouldReturnNewInstance() {
        PythonResultSpec spec1 = PythonResultSpec.create();
        PythonResultSpec spec2 = PythonResultSpec.create();

        assertThat(spec1)
                .isNotSameAs(spec2);
    }

    @Test
    void of_withNameAndType_shouldCreateSpecWithSingleRequirement() {
        PythonResultSpec spec = PythonResultSpec.of("result", String.class);

        assertThat(spec.isEmpty())
                .isFalse();
        assertThat(spec.toMap())
                .hasSize(1)
                .hasEntrySatisfying("result", value -> assertThat(value.type())
                        .isEqualTo(String.class));
    }

    @Test
    void of_withRequirement_shouldCreateSpecWithSingleRequirement() {
        PythonResultRequirement<String> requirement = new PythonResultRequirement<>("result", String.class);

        PythonResultSpec spec = PythonResultSpec.of(requirement);

        assertThat(spec.isEmpty())
                .isFalse();
        assertThat(spec.toMap())
                .hasSize(1)
                .hasEntrySatisfying("result", value -> assertThat(value.type())
                        .isEqualTo(String.class));
    }

    @Test
    void require_shouldAddRequirementToSpec() {
        PythonResultSpec spec = PythonResultSpec.create();

        PythonResultSpec result = spec.require("result", String.class);

        assertThat(result)
                .isSameAs(spec);
        assertThat(spec.toMap())
                .hasSize(1)
                .hasEntrySatisfying("result", value -> assertThat(value.type())
                        .isEqualTo(String.class));
    }

    @Test
    void require_withRequirementObject_shouldAddRequirementToSpec() {
        PythonResultSpec spec = PythonResultSpec.create();
        PythonResultRequirement<Integer> requirement = new PythonResultRequirement<>("result", Integer.class);

        PythonResultSpec result = spec.require(requirement);

        assertThat(result)
                .isSameAs(spec);
        assertThat(spec.toMap())
                .hasSize(1)
                .hasEntrySatisfying("result", value -> assertThat(value.type())
                        .isEqualTo(Integer.class));
    }

    @Test
    void require_shouldSupportChaining() {
        PythonResultSpec spec = PythonResultSpec.create()
                .require("result1", String.class)
                .require("result2", Integer.class)
                .require("result3", Boolean.class);

        assertThat(spec.toMap())
                .hasSize(3)
                .hasEntrySatisfying("result1", value -> assertThat(value.type())
                        .isEqualTo(String.class))
                .hasEntrySatisfying("result2", value -> assertThat(value.type())
                        .isEqualTo(Integer.class))
                .hasEntrySatisfying("result3", value -> assertThat(value.type())
                        .isEqualTo(Boolean.class));
    }

    @Test
    void getRequirement_shouldReturnCorrectRequirement() {
        PythonResultSpec spec = PythonResultSpec.create()
                .require("result1", String.class)
                .require("result2", Integer.class);

        PythonResultRequirement<?> result1 = spec.getRequirement("result1");
        PythonResultRequirement<?> result2 = spec.getRequirement("result2");

        assertThat(result1)
                .isEqualTo(new PythonResultRequirement<>("result1", String.class));
        assertThat(result2)
                .isEqualTo(new PythonResultRequirement<>("result2", Integer.class));
    }

    @Test
    void getRequirement_shouldThrowException_whenNotFound() {
        PythonResultSpec spec = PythonResultSpec.create()
                .require("result", String.class);

        assertThatThrownBy(() -> spec.getRequirement("nonExistent"))
                .isInstanceOf(PythonException.class);
    }

    @Test
    void getRequirement_shouldThrowException_whenSpecIsEmpty() {
        PythonResultSpec spec = PythonResultSpec.empty();

        assertThatThrownBy(() -> spec.getRequirement("result"))
                .hasNoSuppressedExceptions();
    }

    @Test
    void toSet_shouldReturnUnmodifiableSet() {
        PythonResultSpec spec = PythonResultSpec.create()
                .require("result", String.class);

        Map<String, PythonResultRequirement<?>> map = spec.toMap();

        assertThatThrownBy(() -> map.put("result1", new PythonResultRequirement<>("result1", Integer.class)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void toSet_shouldReturnEmptySet_whenSpecIsEmpty() {
        PythonResultSpec spec = PythonResultSpec.empty();

        Map<String, PythonResultRequirement<?>> map = spec.toMap();

        assertThat(map)
                .isEmpty();
    }

    @Test
    void toSet_shouldReturnAllRequirements() {
        PythonResultSpec spec = PythonResultSpec.create()
                .require("result1", String.class)
                .require("result2", Integer.class)
                .require("result3", Boolean.class);

        Map<String, PythonResultRequirement<?>> map = spec.toMap();

        assertThat(map)
                .hasSize(3)
                .hasEntrySatisfying("result1", value -> assertThat(value.type())
                        .isEqualTo(String.class))
                .hasEntrySatisfying("result2", value -> assertThat(value.type())
                        .isEqualTo(Integer.class))
                .hasEntrySatisfying("result3", value -> assertThat(value.type())
                        .isEqualTo(Boolean.class));
    }

    @Test
    void require_shouldReplaceExistingRequirement_whenIdenticalNames() {
        PythonResultSpec spec = PythonResultSpec.create()
                .require("result", String.class)
                .require("result", Integer.class);

        assertThat(spec.toMap())
                .hasSize(1)
                .hasEntrySatisfying("result", value -> assertThat(value.type())
                        .isEqualTo(Integer.class));
    }

    @Test
    void isEmpty_shouldReturnFalse_whenSpecHasRequirements() {
        PythonResultSpec spec = PythonResultSpec.create()
                .require("result", String.class);

        assertThat(spec.isEmpty())
                .isFalse();
    }

    @Test
    void isEmpty_shouldReturnTrue_whenSpecHasNoRequirements() {
        PythonResultSpec spec = PythonResultSpec.create();

        assertThat(spec.isEmpty())
                .isTrue();
    }
}