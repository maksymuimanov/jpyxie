package io.maksymuimanov.python;

import io.maksymuimanov.python.bind.PythonDate;
import io.maksymuimanov.python.deconverter.PythonDateDeconverter;
import io.maksymuimanov.python.deserializer.BasicPythonDeserializer;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
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
            // Classic and common
            "01-02-2025",
            "2025-02-01",
            "02/01/2025",
            "01/02/2025",
            "01.02.2025",
            "2025.02.01",

            // ISO-ish variants
            "20250201",
            "250201",        // YYMMDD
            "010225",        // DDMMYY

            // Dots, slashes, dashes — chaos edition
            "01_02_2025",
            "01 02 2025",
            "01\\02\\2025",
            "01—02—2025",    // em dash
            "01‧02‧2025",    // weird dot

            // Mixed separators — the little gremlins of validation
            "01/02-2025",
            "01.02/2025",
            "2025/02.01",

            // Month names
            "01-Feb-2025",
            "1-Feb-2025",
            "01-February-2025",
            "February-01-2025",
            "01 Feb 2025",
            "Feb 01 2025",
            "1 Feb 2025",

            // Upper/lower chaos
            "01-feb-2025",
            "01-FEB-2025",
            "feb-01-2025",

            // Ordinals
            "1st Feb 2025",
            "2nd Feb 2025",
            "3rd Feb 2025",
            "4th Feb 2025",

            // Compact oddities
            "2025/1/2",
            "1/2/2025",
            "1-2-25",
            "25-1-2",

            // With commas (US newspaper vibes)
            "February 1, 2025",
            "1 February, 2025",

            // Weird but technically doable
            "2025-Feb-01",
            "2025/Feb/01",
            "01/Feb/25",
            "2025‒02‒01",     // figure dash
            "01⁄02⁄2025",     // fraction slash

            // Space-heavy formats
            "01   02   2025",
            "2025    02    01",

            // Exotic numeric look
            "01•02•2025",
            "01⁄02⁄25",
            "2025년02월01일",  // Korean style but still day-month-year components
            "01日02月2025年"   // Inverted but contains day, month, year
    })
    void test2(String date) {
        PythonDateDeconverter pythonDateDeconverter = new PythonDateDeconverter();
        PythonDate resolve = pythonDateDeconverter.resolve(date, null);
        System.out.println(resolve.getValue());
    }
}