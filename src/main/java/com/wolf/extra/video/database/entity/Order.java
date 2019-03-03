package com.wolf.extra.video.database.entity;

/**
 * 订单信息表
 * @author tianya
 *
 */
public class Order {

	/**
	 * 订单id UUID
	 */
	private String orderId;

	/**
	 * 视频平台订单号
	 */
	private String orderNumber;

	/**
	 * 支付平台订单号
	 */
	private String orderNumberPayment;

	/**
	 * 视频id UUID
	 */
	private String videoId;

	/**
	 * 支付渠道
	 * 1 支付宝, 2财付通, 3 微信
	 */
	private String payType;

	/**
	 * 价格
	 */
	private String price;

	/**
	 * 订单完成状态
	 * 0：发起订单
	 * 1：完成支付
	 */
	private int status;

	/**
	 * 订单用户ip
	 */
	private String userIp;

	/**
	 * 订单生成时间
	 * 当用户发起订单
	 */
	private long createTime;

	/**
	 * 订单完成时间
	 * 当完成支付
	 */
	private long completeTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderNumberPayment() {
		return orderNumberPayment;
	}

	public void setOrderNumberPayment(String orderNumberPayment) {
		this.orderNumberPayment = orderNumberPayment;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(long completeTime) {
		this.completeTime = completeTime;
	}

}
