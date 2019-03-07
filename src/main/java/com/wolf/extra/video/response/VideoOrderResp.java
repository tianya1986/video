package com.wolf.extra.video.response;

import com.wolf.common.utils.EnandeUtil;
import com.wolf.extra.video.database.entity.Order;
import com.wolf.extra.video.database.entity.Video;

public class VideoOrderResp {

	private Video video;

	private Order order;

	private String host = "www.hyf0w4o.top";

	/**
	 * 验证码，用来验证是否已付过款
	 */
	private String code;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCode() {
		return code;
	}

	public void generateCode() {
		if (order != null && video != null && video.getDentry() != null) {
			String orderId = order.getOrderId();
			String videoId = video.getVideoId();
			String orderNumberPayment = order.getOrderNumberPayment();

			code = EnandeUtil.encrypt(orderId + "|" + videoId + "|"
					+ orderNumberPayment);
		}
	}

	public static String[] encrypt(String code) {
		if (code != null && code.length() > 0) {
			String result = EnandeUtil.decrypt(code);
			return result.split("\\|");
		}
		return null;
	}

}
