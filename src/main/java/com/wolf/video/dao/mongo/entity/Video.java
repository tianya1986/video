package com.wolf.video.dao.mongo.entity;

/**
 * 视频信息
 */
public class Video {

	// 字段名，与属性名必须一致。请谨慎修改
	public static final String	ID			= "videoId";
	public static final String	DENTRY_Id	= "dentryId";
	public static final String	VIDEO_NAME	= "videoName";
	public static final String	PRICE		= "price";
	public static final String	PAY_TOTAL	= "payTotal";
	public static final String	STATUS		= "status";

	private String				videoId;					// 视频id （UUID）
	private String				dentryId;					// 文件id
	private String				videoName;					// 视频名称
	private float				price;						// 价格
	private int					payTotal;					// 支付数量

	/**
	 * 视频状态
	 * {@link com.wolf.extra.video.Status}}
	 */
	private String				status;
	private long				createTime;				// 创建时间

	public static final class Status {
		public static final String	SALE_OFF	= "sale_off";	// 下架
		public static final String	SALE_ON		= "sale_on";	// 上架
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getDentryId() {
		return dentryId;
	}

	public void setDentryId(String dentryId) {
		this.dentryId = dentryId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(int payTotal) {
		this.payTotal = payTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
