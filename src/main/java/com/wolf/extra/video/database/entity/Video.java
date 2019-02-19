package com.wolf.extra.video.database.entity;

import java.io.Serializable;

public class Video implements Serializable {

	
	private String _id;
	
	/**
	 * 视频名
	 */
	private String name;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 视频长度
	 */
	private long length;

	/**
	 * 文件后缀名
	 */
	private String suffix;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getId() {
		return _id;
	}
	
	
}
