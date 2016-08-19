package com.tustar.demo.util;

/**
 * Created by tustar on 16-8-17.
 */
public class SqliteSpecialCharUtils {

    public static String escape(String keyWord) {
        keyWord = keyWord.replace("/", "//");
        keyWord = keyWord.replace("'", "''");
        keyWord = keyWord.replace("[", "/[");
        keyWord = keyWord.replace("]", "/]");
        keyWord = keyWord.replace("%", "/%");
        keyWord = keyWord.replace("&", "/&");
        keyWord = keyWord.replace("_", "/_");
        keyWord = keyWord.replace("(", "/(");
        keyWord = keyWord.replace(")", "/)");
        return keyWord;
    }

    public static boolean containsSpecialChar(String keyWord) {
        if (keyWord.contains("/")) {
            return true;
        }

        if (keyWord.contains("'")) {
            return true;
        }

        if (keyWord.contains("[")) {
            return true;
        }

        if (keyWord.contains("]")) {
            return true;
        }

        if (keyWord.contains("%")) {
            return true;
        }

        if (keyWord.contains("&")) {
            return true;
        }

        if (keyWord.contains("_")) {
            return true;
        }

        if (keyWord.contains("(")) {
            return true;
        }

        if (keyWord.contains(")")) {
            return true;
        }
        
        return false;
    }
}
