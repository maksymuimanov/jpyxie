package io.maksymuimanov.python;

import io.maksymuimanov.python.bind.PythonDate;
import io.maksymuimanov.python.bind.PythonTime;
import io.maksymuimanov.python.deconverter.PythonDateDeconverter;
import io.maksymuimanov.python.deconverter.PythonTimeDeconverter;
import io.maksymuimanov.python.deserializer.BasicPythonDeserializer;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestDeTest {
    @Test
    void test() {
        String a = """
                {
                \t"text": "3q5q3tqt",
                \t"goofy": "I am Goofy! :DDDDDDDDDDD",
                \t"b": {
                \t\t"b": None
                \t},
                \t"ameba": None,
                \t"x": 125,
                \t"optNum": 125134,
                \t"empty": None,
                \t"u": -15000.0,
                \t"z": False,
                \t"t": "t",
                \t"textArray": ["a", "b", "c"],
                \t"intArray": [1, 35, 315135],
                \t"bArray": [{
                \t\t"b": "Abafa"
                \t}, {
                \t\t"b": None
                \t}, {
                \t\t"b": "Tbart"
                \t}],
                \t"iterable": {4, 6, 22},
                \t"list": [1, 2, 3],
                \t"set": {3, 2, 1},
                \t"queue": deque([1, 2, 3]),
                \t"map": {
                \t\t3: 4,
                \t\t1: 2
                \t},
                \t"cccA": {
                \t\t"enum4java": "A"
                \t},
                \t"cccC": {
                \t\t"enum4java": "C"
                \t},
                \t"date": date(2025, 11, 28)
                }""";

        PythonDeserializer pythonDeserializer = new BasicPythonDeserializer();
        System.out.println(pythonDeserializer.represent(a).toPythonString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "01-01-2020",
            "31-12-1999",
            "12/31/1999",
            "1999-12-31",
            "2020/01/01",
            "01.01.2020",
            "31.12.1999",
            "1-1-2020",
            "31-1-2020",
            "1/12/2020",
            "12/1/2020",
            "2020.01.01",
            "2020-1-1",
            "2020/1/1",
            "01-Jan-2020",
            "31-Dec-1999",
            "1-Feb-2000",
            "29-Feb-2004",
            "30-Apr-2020",
            "15-Aug-1947",
            "01/Jan/2020",
            "31/Dec/1999",
            "2020 Jan 01",
            "1999 Dec 31",
            "1 Jan 2020",
            "31 Dec 1999",
            "01-Jan-20",
            "31-Dec-99",
            "1-Feb-00"
    })
    void test2(String date) {
        PythonDateDeconverter pythonDateDeconverter = new PythonDateDeconverter();
        PythonDate resolve = pythonDateDeconverter.resolve(date, null);
        System.out.println(resolve.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "00:00:00",
            "23:59:59",
            "12:30:45",
            "1:2:3",
            "01:02:03",
            "00:00",
            "23:59",
            "7:5",
            "07:05",
            "12:34:56.789",
            "01:02:03.000123",
            "12-34-56",
            "12.34.56",
            "12|34|56",
            "12:34:56:789", // лишние части
            "0:0:0",
            "9:8:7",
            "23:0:0",
            "0:59:59",
            "15:30",
            "15.30",
            "15-30",
            "15|30",
            "12:34:56.1",
            "12:34:56.12",
            "12:34:56.123",
            "12:34:56.123456",
            "12:34:56,789",
            "12:34:56;789"
    })
    void testTime(String time) {
        PythonTimeDeconverter pythonTimeDeconverter = new PythonTimeDeconverter();
        Assumptions.assumeTrue(pythonTimeDeconverter.matches(time));
        PythonTime resolve = pythonTimeDeconverter.resolve(time, null);
        System.out.println(resolve.getValue());
    }
}