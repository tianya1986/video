package com.wolf.extra.order.helper;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolf.common.utils.EnandeUtil;
import com.wolf.common.utils.StringUtil;
import com.wolf.extra.order.OrderConfig;
import com.wolf.extra.order.model.OrderV2;
import com.wolf.extra.video.controller.OrderController;

public final class OrderHelper {

	private static Logger		logger	= LoggerFactory.getLogger(OrderController.class);
	private final static String	TAG		= "OrderHelper";

	public static String makeCode(OrderV2 order) {
		if (order != null) {
			String orderId = order.getOrderId();
			String videoId = order.getVideoId();
			String orderNumber = order.getOrderNumber();

			return EnandeUtil.encrypt(orderId + "$" + videoId + "$" + orderNumber);
		}
		return null;
	}

	public static String getOrderId(String code) {
		if (!StringUtil.isEmpty(code)) {
			String result = EnandeUtil.decrypt(code);
			String[] array = result.split("&");
			return array[0];
		}
		return null;
	}

	/**
	 * 生成订单编号
	 * @return
	 */
	public static String makeOrderNumber() {
		String orderNumber = System.currentTimeMillis() + "";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			orderNumber += random.nextInt(10);
		}
		return orderNumber;
	}

	public static String makeOrderId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static boolean isExpire(OrderV2 order) {
		if (order == null || OrderV2.Status.PAID.equals(order.getStatus())) {
			return false;
		}
		return isExpire(order.getCompleteTime());
	}

	public static boolean isExpire(long completeTime) {
		long nowTime = System.currentTimeMillis();
		long expireTime = OrderConfig.getExpireTime(); // 1小时

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
}
