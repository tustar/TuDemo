package com.tustar.util;

/**
 * Created by tustar on 4/9/16.
 */
public class ColorUtils {

    public static String rgbToHex(int color) {
        return "#" + Integer.toHexString(color).toUpperCase();
    }
}
