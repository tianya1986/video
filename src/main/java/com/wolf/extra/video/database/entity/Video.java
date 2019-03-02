package com.wolf.extra.video.database.entity;

import com.wolf.cs.entity.Dentry;

/**
 * 视频信息
 */
public class Video {

	@SuppressWarnings("unused")
	private String _id;

	/**
	 * 视频id （UUID）
	 */
	private String videoId;

	/**
	 * 文件id
	 */
	private String dentryId;
	
	private Dentry dentry;

	/**
	 * 视频名称
	 */
	private String name;

	/**
	 * 价格
	 */
	private float price;

	/**
	 * 短地址
	 */
	private String shortURL;
	
	/**
	 * 对应域名
	 */
	private String domain;

	/**
	 * 点击量
	 */
	private long clickNumber;

	/**
	 * 视频状态
	 * {@link com.wolf.extra.video.Status}}
	 */
	private String status;
	
	private boolean isNew;

	/**
	 * 视频创建时间
	 */
	private long createTime;

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
	
	public Dentry getDentry() {
		return dentry;
	}
	
	public void setDentry(Dentry dentry) {
		this.dentry = dentry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getShortURL() {
		return shortURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public long getClickNumber() {
		return clickNumber;
	}

	public void setClickNumber(long clickNumber) {
		this.clickNumber = clickNumber;
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
