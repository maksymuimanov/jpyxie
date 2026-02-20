package io.jpyxie.python.processor;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

class PythonResultTest {
    @Test
    void present_shouldCreateResult_whenValueIsNotNull() {
        PythonResult<String> result = PythonResult.present("testName", "testValue");

        assertThat(result.getName())
                .isEqualTo("testName");
        assertThat(result.getBody())
                .isEqualTo("testValue");
        assertThat(result.getType())
                .isEqualTo(String.class);
        assertThat(result.isPresent())
                .isTrue();
        assertThat(result.isAbsent())
                .isFalse();
    }

    @Test
    void present_shouldCreateAbsentResult_whenValueIsNull() {
        PythonResult<String> result = PythonResult.present("testName", null);

        assertThat(result.getName())
                .isEqualTo("testName");
        assertThat(result.getBody())
                .isNull();
        assertThat(result.getType())
                .isEqualTo(Void.class);
        assertThat(result.isPresent())
                .isFalse();
        assertThat(result.isAbsent())
                .isTrue();
    }

    @Test
    void absent_shouldCreateAbsentResult() {
        PythonResult<?> result = PythonResult.absent("testName");

        assertThat(result.getName())
                .isEqualTo("testName");
        assertThat(result.getBody())
                .isNull();
        assertThat(result.getType())
                .isEqualTo(Void.class);
        assertThat(result.isPresent())
                .isFalse();
        assertThat(result.isAbsent())
                .isTrue();
    }

    @Test
    void getBodyOrElse_shouldReturnValue_whenPresent() {
        PythonResult<String> result = PythonResult.present("testName", "actualValue");
        Supplier<String> supplier = () -> "defaultValue";

        String value = result.getBodyOrElse(supplier);

        assertThat(value)
                .isEqualTo("actualValue");
    }

    @Test
    void getBodyOrElse_shouldReturnSupplierValue_whenNull() {
        PythonResult<String> result = PythonResult.present("testName", null);
        Supplier<String> supplier = () -> "defaultValue";

        String value = result.getBodyOrElse(supplier);

        assertThat(value)
                .isEqualTo("defaultValue");
    }

    @Test
    void getBodyOrElse_shouldCallSupplierOnlyWhenAbsent() {
        PythonResult<String> presentResult = PythonResult.present("testName", "value");
        PythonResult<String> absentResult = PythonResult.present("testName", null);
        Supplier<String> supplier = () -> {
            throw new RuntimeException("Supplier should not be called");
        };

        assertThatCode(() -> presentResult.getBodyOrElse(supplier))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> absentResult.getBodyOrElse(supplier))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Supplier should not be called");
    }

    @Test
    void equals_shouldReturnTrueForIdenticalResults() {
        PythonResult<String> result1 = PythonResult.present("name", "value");
        PythonResult<String> result2 = PythonResult.present("name", "value");

        assertThat(result1)
                .isEqualTo(result2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentNames() {
        PythonResult<String> result1 = PythonResult.present("name1", "value");
        PythonResult<String> result2 = PythonResult.present("name2", "value");

        assertThat(result1)
                .isNotEqualTo(result2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentBodies() {
        PythonResult<String> result1 = PythonResult.present("name", "value1");
        PythonResult<String> result2 = PythonResult.present("name", "value2");

        assertThat(result1)
                .isNotEqualTo(result2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentTypes() {
        PythonResult<String> result1 = PythonResult.present("name", "value");
        PythonResult<Integer> result2 = PythonResult.present("name", 42);

        assertThat(result1)
                .isNotEqualTo(result2);
    }

    @Test
    void equals_shouldReturnFalseForNull() {
        PythonResult<String> result = PythonResult.present("name", "value");

        assertThat(result)
                .isNotEqualTo(null);
    }

    @Test
    void equals_shouldReturnFalseForDifferentClass() {
        PythonResult<String> result = PythonResult.present("name", "value");

        assertThat(result)
                .isNotEqualTo("string");
    }

    @Test
    void hashCode_shouldBeSameForEqualResults() {
        PythonResult<String> result1 = PythonResult.present("name", "value");
        PythonResult<String> result2 = PythonResult.present("name", "value");

        assertThat(result1.hashCode())
                .isEqualTo(result2.hashCode());
    }

    @Test
    void hashCode_shouldBeDifferentForDifferentResults() {
        PythonResult<String> result1 = PythonResult.present("name1", "value");
        PythonResult<String> result2 = PythonResult.present("name2", "value");

        assertThat(result1.hashCode())
                .isNotEqualTo(result2.hashCode());
    }

    @Test
    void hashCode_shouldIncludeAllFields() {
        PythonResult<String> result = PythonResult.present("name", "value");
        int expectedHashCode = Objects.hash("name", "value", String.class);

        assertThat(result.hashCode())
                .isEqualTo(expectedHashCode);
    }

    @Test
    void toString_shouldContainAllFields() {
        PythonResult<String> result = PythonResult.present("testName", "testValue");
        String stringRepresentation = result.toString();

        assertThat(stringRepresentation)
                .contains("testName")
                .contains("testValue")
                .contains("String");
    }

    @Test
    void toString_shouldHandleNullBody() {
        PythonResult<?> result = PythonResult.absent("testName");
        String stringRepresentation = result.toString();

        assertThat(stringRepresentation)
                .contains("testName")
                .contains("null")
                .contains("Void");
    }

    @Test
    void present_shouldInferTypeFromValue() {
        PythonResult<Integer> intResult = PythonResult.present("intName", 42);
        PythonResult<Double> doubleResult = PythonResult.present("doubleName", 3.14);
        PythonResult<Boolean> boolResult = PythonResult.present("boolName", true);

        assertThat(intResult.getType())
                .isEqualTo(Integer.class);
        assertThat(doubleResult.getType())
                .isEqualTo(Double.class);
        assertThat(boolResult.getType())
                .isEqualTo(Boolean.class);
    }

    @Test
    void present_shouldHandleObjectValue() {
        TestObject testObject = new TestObject("test");
        PythonResult<TestObject> result = PythonResult.present("objectName", testObject);

        assertThat(result.getType())
                .isEqualTo(TestObject.class);
        assertThat(result.getBody())
                .isSameAs(testObject);
    }

    @Test
    void isPresent_shouldBeTrueForPresentResults() {
        PythonResult<String> result = PythonResult.present("name", "value");

        assertThat(result.isPresent())
                .isTrue();
        assertThat(result.isAbsent())
                .isFalse();
    }

    @Test
    void isAbsent_shouldBeTrueForAbsentResults() {
        PythonResult<?> result = PythonResult.absent("name");

        assertThat(result.isAbsent())
                .isTrue();
        assertThat(result.isPresent())
                .isFalse();
    }

    @Test
    void getBody_shouldReturnActualValue() {
        String value = "testValue";
        PythonResult<String> result = PythonResult.present("name", value);

        assertThat(result.getBody())
                .isSameAs(value);
    }

    @Test
    void getBody_shouldReturnNullForAbsentResults() {
        PythonResult<?> result = PythonResult.absent("name");

        assertThat(result.getBody())
                .isNull();
    }

    @Test
    void getName_shouldReturnProvidedName() {
        String name = "testName";
        PythonResult<String> result = PythonResult.present(name, "value");

        assertThat(result.getName())
                .isEqualTo(name);
    }

    @Test
    void getType_shouldReturnCorrectType() {
        PythonResult<String> result = PythonResult.present("name", "value");

        assertThat(result.getType())
                .isEqualTo(String.class);
    }

    private record TestObject(String value) {

        @Override
            public String toString() {
                return "TestObject{" + "value='" + value + '\'' + '}';
            }
        }
}
