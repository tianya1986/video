package com.wolf.extra.video.database.entity;

/**
 * 视频域名
 * 多个域名可以访问视频，避免域名被封
 * @author tianya
 */
public class VideoDomain {

	/**
	 * 域名id  UUID
	 */
	private String domainId;

	/**
	 * 域名
	 */
	private String domain;

	/**
	 * 创建时间
	 */
	private long createTime;

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
