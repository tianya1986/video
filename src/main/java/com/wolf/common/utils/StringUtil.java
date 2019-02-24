package com.wolf.common.utils;

public final class StringUtil {

	public static boolean isEmpty(String value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		return false;
	}

	public static int parseInt(String value) {
		if (value == null || value.length() == 0) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
		}
		return 0;
	}
}
