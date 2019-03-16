package com.wolf.extra.order.payjs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.DigestUtils;

public class SignUtil {

	private static String	mchid	= "1524053761";
	private static String	key		= "iavZ0F9LAAtFE76O";
	private static String	payUrl	= "https://payjs.cn/api/cashier";

	public static String getPayUrl(String orderId, int price,
			String orderNumber,
			String body,
			String host) {
		String notifyUrl = "http://www.lincolor112.live/v2/order/payjs/complete";
		String callbackUrl = "http://" + host + "/manager/video/v2/play-forward.html?orderId=" + orderId;
		
		Map<String, String> map = new HashMap<>();
		map.put("mchid", mchid);
		map.put("notify_url", notifyUrl);// 请注意，，该路径需要payjs服务器可以直接访问，且结果为200。测试地址不行，www.baidu.com也不行
		map.put("out_trade_no", orderId);
		map.put("total_fee", "" + price);
		map.put("callback_url", callbackUrl);
		String sign = SignUtil.sign(map, key).toUpperCase();

		return payUrl + "?mchid=" + mchid + "&notify_url=" + notifyUrl + "&callback_url=" + callbackUrl + "&out_trade_no=" + orderId + "&total_fee=" + price + "&sign=" + sign;
	}

	private static String sign(Map<String, String> map,
			String privateKey) {
		Collection<String> keyset = map.keySet();
		List<String> keyList = new ArrayList<>(keyset);
		Collections.sort(keyList);
		StringBuilder sb = new StringBuilder();
		for (String key : keyList) {
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
		sb.append("key=").append(privateKey);
		return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
	}

}
