package com.tustar.util;

/**
 * Created by tustar on 16-7-6.
 */
public class ArrayUtils {

    public ArrayUtils() {
        super();
    }

    public static void reverse(final String[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    public static void reverse(final String[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        String tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }
}
