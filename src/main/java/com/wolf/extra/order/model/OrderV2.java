package com.wolf.extra.order.model;

import com.sun.javafx.collections.MappingChange.Map;

/**
 * 订单信息
 * 
 * 创建订单请填写以下必要信息
 * videoId
 * orderId
 * orderNumber
 * status
 * createTime
 * @author tianya
 *
 */
public class OrderV2 {

	public class Status {
		public static final String	PAID				= "PAID";				// 已支付
		public static final String	WAITING_CALLBACK	= "WAITING_CALL_BACK";	// 支付成功，等待平台回调
		public static final String	UN_PAID				= "UN_PAID";			// 未支付
	}

	private String				_id;				// mongodb 唯一键
	private String				uid;				// 用户id
	private String				platform;
	private String				videoId;			// 视频id UUID
	private String				orderId;			// 订单id UUID
	private String				orderNumber;		// 视频平台订单号
	private String				orderNumberPayment; // 支付平台订单号
	private String				payType;			// 支付渠道 1 支付宝, 2财付通, 3 微信
	private int					price;				// 价格 单位：分
	private String				status;			// 订单完成状态
	private long				completeTime;		// 订单完成时间
	private String				completeTimeStr;	// 订单完成时间
	private Map<String, Object>	expInfo;			// 拓展参数
	private String				ip;				// 订单用户ip
	private String				createTimeStr;		// 订单生成时间
	private long				createTime;		// 订单生成时间

	public String get_id() {
		return _id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

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

	public int getPrice() {
		return price;
	}

	public String getCompleteTimeStr() {
		return completeTimeStr;
	}

	public void setCompleteTimeStr(String completeTimeStr) {
		this.completeTimeStr = completeTimeStr;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(long completeTime) {
		this.completeTime = completeTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Map<String, Object> getExpInfo() {
		return expInfo;
	}

	public void setExpInfo(Map<String, Object> expInfo) {
		this.expInfo = expInfo;
	}

}
