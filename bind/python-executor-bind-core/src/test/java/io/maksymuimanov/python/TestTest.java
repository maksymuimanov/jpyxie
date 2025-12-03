package io.maksymuimanov.python;

import io.maksymuimanov.python.annotation.PythonConvert;
import io.maksymuimanov.python.annotation.PythonIgnore;
import io.maksymuimanov.python.annotation.PythonInclude;
import io.maksymuimanov.python.bind.PythonString;
import io.maksymuimanov.python.converter.*;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.BasicPythonSerializer;
import io.maksymuimanov.python.serializer.PythonSerializer;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class TestTest {
    @Test
    void test() {
        List<PythonTypeConverter> typeConverters = List.of(
                new PythonBigDecimalConverter(),
                new PythonBigIntegerConverter(),
                new PythonBooleanConverter(),
                new PythonDictionaryConverter(),
                new PythonFloatConverter(),
                new PythonIntConverter(),
                new PythonListConverter(),
                new PythonObjectDictionaryConverter(),
                new PythonQueueConverter(),
                new PythonSetConverter(),
                new PythonStringConverter(),
                new PythonOptionalConverter(),
                new PythonArrayConverter(),
                new GoofyConverter(),
                new PythonLocalDateConverter(),
                new PythonEnumDictionaryConverter()
        );

        BasicPythonSerializer serializer = new BasicPythonSerializer(typeConverters);

//        System.out.println(serializer.serialize(1).toPythonString());
//        System.out.println(serializer.serialize("a2145a").toPythonString());
//        System.out.println(serializer.serialize(new int[]{1, 2, 3}).toPythonString());


        A a = new A();
        PythonRepresentation representation = serializer.serialize(a);
        System.out.println(representation.toPythonString());
    }

    public static class GoofyConverter implements PythonTypeConverter {
        @Override
        public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
            return new PythonString("I am Goofy! :DDDDDDDDDDD");
        }

        @Override
        public boolean supports(Class<?> type) {
            return JavaTypeUtils.isString(type);
        }
    }

//    @PythonInclude(visibleFields = PythonInclude.AccessModifier.PROTECTED)
    public static class A implements Serializable {
        @PythonIgnore
        private final String a = "aefafa";
        private final String text = "3q5q3tqt";
        @PythonConvert(GoofyConverter.class)
        private final String goofy = "ge";
        private final B b = new B();
        protected A ameba;
        public int x = 125;
        public Optional<Integer> optNum = Optional.of(125134);
        public Optional<?> empty = Optional.empty();
        @PythonIgnore
        public byte y = -1-5;
        private final double u = -15.e3;
        protected boolean z = false;
        public char t = 't';
        public char[] textArray = new char[]{'a', 'b', 'c'};
        public int[] intArray = new int[]{1, 35, 315135};
        public B[] bArray = new B[]{new B("Abafa"), new B(), new B("Tbart")};
        public Iterable<Integer> iterable = Set.of(4, 22, 6);
        public List<Integer> list = List.of(1, 2, 3);
        public Set<Integer> set = Set.of(1, 2, 3);
        public Queue<Integer> queue = new PriorityQueue<>(list);
        public Map<Integer, Integer> map = Map.of(1, 2, 3, 4);
        public Ccc cccA = Ccc.A;
        public Ccc cccC = Ccc.C;
        public LocalDate date = LocalDate.now();
    }

    public static class B {
        private String b;

        public B() {
        }

        public B(String b) {
            this.b = b;
        }
    }

    @PythonInclude(visibleFields = PythonInclude.AccessModifier.PROTECTED)
    public enum Ccc {
        A(1, "TRT\"214", Ddd.A),
        B(2, "#T2#", Ddd.B),
        C(3, "3#%'35", Ddd.C);

        private final int x;
        private final String y;
        private final Ddd ddd;

        Ccc(int x, String y, Ddd ddd) {
            this.x = x;
            this.y = y;
            this.ddd = ddd;
        }
    }

    public enum Ddd {
        A, B, C;

        int numb;

        Ddd() {
            this.numb = ordinal();
        }
    }
}
