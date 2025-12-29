package io.maksymuimanov.python.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CharSequenceUtils {
    public static final int START_INDEX = 0;
    public static final int NOT_FOUND_INDEX = -1;

    public static boolean startsWith(CharSequence charSequence, CharSequence prefix) {
        int prefixLen = prefix.length();
        if (prefixLen > charSequence.length()) return false;
        for (int i = 0; i < prefixLen; i++) {
            if (charSequence.charAt(i) != prefix.charAt(i)) return false;
        }
        return true;
    }

    public static boolean endsWith(CharSequence charSequence, CharSequence suffix) {
        int length = charSequence.length();
        int suffixLength = suffix.length();
        if (suffixLength > length) return false;
        for (int i = 1; i <= suffixLength; i++) {
            if (charSequence.charAt(length - i) != suffix.charAt(suffixLength - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean contains(CharSequence charSequence, CharSequence needed) {
        return indexOf(charSequence, needed) != NOT_FOUND_INDEX;
    }


    public static int indexOf(CharSequence charSequence, CharSequence needed) {
        int length = charSequence.length(), neededLength = needed.length();
        if (neededLength == START_INDEX) return START_INDEX;
        if (neededLength > length) return NOT_FOUND_INDEX;

        int[] lps = computeLPSArray(needed);
        int currentIndex = START_INDEX, neededIndex = START_INDEX;

        while (currentIndex < length) {
            if (charSequence.charAt(currentIndex) == needed.charAt(neededIndex)) {
                currentIndex++;
                neededIndex++;
                if (neededIndex == neededLength) {
                    return currentIndex - neededLength;
                }
            } else {
                if (neededIndex != START_INDEX) {
                    neededIndex = lps[neededIndex - 1];
                } else {
                    currentIndex++;
                }
            }
        }

        return NOT_FOUND_INDEX;
    }

    private static int[] computeLPSArray(CharSequence needed) {
        int neededLength = needed.length();
        int[] lps = new int[neededLength];
        int currentLength = START_INDEX;
        int currentIndex = 1;

        while (currentIndex < neededLength) {
            if (needed.charAt(currentIndex) == needed.charAt(currentLength)) {
                currentLength++;
                lps[currentIndex] = currentLength;
                currentIndex++;
            } else {
                if (currentLength != START_INDEX) {
                    currentLength = lps[currentLength - 1];
                } else {
                    lps[currentIndex] = START_INDEX;
                    currentIndex++;
                }
            }
        }

        return lps;
    }

    public static boolean equals(CharSequence o1, CharSequence o2) {
        int len = o1.length();
        if (len != o2.length()) return false;
        for (int i = 0; i < len; i++) {
            if (o1.charAt(i) != o2.charAt(i)) return false;
        }
        return true;
    }

    public static boolean matches(CharSequence charSequence, CharSequence pattern) {
        Pattern compiledPattern = Pattern.compile(pattern.toString());
        Matcher matcher = compiledPattern.matcher(charSequence);
        return matcher.matches();
    }

    private CharSequenceUtils() {
    }
}
