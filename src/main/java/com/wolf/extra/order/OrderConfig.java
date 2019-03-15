package com.wolf.extra.order;

public class OrderConfig {
	public static final long	EXPIRE_TIME	= 1000 * 60 * 60 * 12;

	public static long getExpireTime() {
		return EXPIRE_TIME;
	}

	public static class Platform {
		public final static String	PAYJS	= "payjs";
		public final static String	UPAY	= "upay";
	}

	public static class PayType {
		public final static String	WEIXIN	= "weixin"; // 微信
		public final static String	ALIPAY	= "alipay"; // 支付宝
	}
}
