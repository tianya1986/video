package com.wolf.extra.video.database.entity;

import java.io.Serializable;

public class Video implements Serializable {

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
	private int length;

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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
