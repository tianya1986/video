package com.wolf.common.utils;

public final class StringUtil {

    public static boolean isEmpty(String value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        return false;
    }
}
