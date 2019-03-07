package com.wolf.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * TODO 该加密解密有BUG
 * @author tianya
 *
 */
public final class EnandeUtil {

	public static boolean isExpire(long completeTime) {
		long nowTime = System.currentTimeMillis();
		 long expireTime = 1000 * 60 * 60 * 24; // 24小时
		// long expireTime = 1000 * 60 * 60; // 1小时
//		long expireTime = 1000 * 60 * 5; // 5分钟
		if ((nowTime - completeTime) / expireTime > 1) {
			return true;
		}
		return false;
	}

	/**
	 * @param sourceString
	 * @param password
	 * @return 加密
	 */
	public static String encrypt(String sourceString) {
		String encoded = null;
		try {
			byte[] bytes = sourceString.getBytes("UTF-8");
			encoded = Base64.getEncoder().encodeToString(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encoded;
	}

	/**
	 *
	 * @param sourceString
	 * @param password
	 * @return 解密
	 */
	public static String decrypt(String sourceString) {
		byte[] decoded = null;
		try {
			byte[] bytes = sourceString.getBytes("UTF-8");
			decoded = Base64.getDecoder().decode(bytes);
		} catch (Exception e) {
		}

		return new String(decoded);
	}

	public static void main(String[] args) {
		String code = "6311e3a313254dbdbdc35113c8746dd5|"
				+ "6ac131f4e18a43279b75302c7027fc3e|" + "123";
		String result = encrypt(code);
		System.out.println(result);

		System.out.println(decrypt(result));
	}

}
