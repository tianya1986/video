package com.wolf.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolf.extra.video.controller.OrderController;

/**
 * @author tianya
 *
 */
public class EnandeUtil {

	private static Logger logger = LoggerFactory.getLogger(OrderController.class);
	private final static String TAG = "EnandeUtil";

	/**
	 * 从支付服务器获取订单信息
	 * @param orderId
	 * @return
	 */
	public Map<String, String> getOrderPayment(String orderId) {
		String requestUrl = "http://www.hyf0w4o.top/ldpay/validate-order.php?orderId=" + orderId;
		Map<String, String> params = new HashMap<String, String>();
		try {
			URL http = new URL(requestUrl);
			// 打开连接
			HttpURLConnection urlConnection = (HttpURLConnection) http.openConnection();

			if (200 == urlConnection.getResponseCode()) {
				// 得到输入流
				InputStream is = urlConnection.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = is.read(buffer))) {
					baos.write(buffer, 0, len);
					baos.flush();
				}
				String result = baos.toString("utf-8");
				if (result != null) {
					String[] array = result.split("&");
					if (array != null && array.length > 0) {
						for (int i = 0; i < array.length; i++) {
							String[] keyValue = array[i].split("=");
							if (keyValue != null && keyValue.length > 0) {
								String key = keyValue[0];
								String value = "";
								if (keyValue.length > 1) {
									value = keyValue[1];
								}

								params.put(key, value);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return params;
	}

	public static void main(String[] args) {
		String value = decrypt("N2Q3MjM2Mjk0YzZlNDcwNGE3YTczODY1MmUxMGM2Njh8ZGNmMDdmNWQ0MzBjNDE5OThhMTMxOWNlMmUzNDQ1OTR8MTAwMDAxNTUyMTQyNzM3NTM3ODU4OTE4MTYwMDgwNDI0NDMyNQ==");
		System.out.println(value);
	}

	public static boolean isExpire(long completeTime) {
		long nowTime = System.currentTimeMillis();
		// long expireTime = 1000 * 60 * 60 * 24; // 24小时
		long expireTime = 1000 * 60 * 60 * 12; // 1小时
//		 long expireTime = 1000 * 60 * 60; // 1小时
//		long expireTime = 1000 * 60 * 5; // 5分钟

		logger.info(TAG + " Validate order expire, now time = " + nowTime);
		logger.info(TAG + " Validate order expire, complete time = " + completeTime);
		logger.info(TAG + " Validate order expire, expire time = 1000 * 60 * 5 (" + expireTime + ")");
		logger.info(TAG + " Validate order expire, nowTime - completeTime = " + (nowTime - completeTime));
		logger.info(TAG + " Validate order expire, (nowTime - completeTime) / expireTime = " + (nowTime - completeTime) / expireTime);
		if ((nowTime - completeTime) / expireTime >= 1) {
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

}
