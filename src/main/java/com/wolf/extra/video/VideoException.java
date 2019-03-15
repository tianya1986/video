package com.wolf.extra.video;

public class VideoException extends Exception {

	private String	code;

	public VideoException(String message) {
		super(message);
	}

	public VideoException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	private static final long	serialVersionUID	= 5597913338870146914L;

}
