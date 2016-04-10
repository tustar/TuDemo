package com.tustar.demo.utils;

/**
 * Created by tustar on 4/9/16.
 */
public class ColorUtil {

    public static String rgbToHex(int color) {
        return "#" + Integer.toHexString(color).toUpperCase();
    }
}
