package com.wolf;

/**
 * 接口统一响应类
 * @author tianya
 */
public class Response {

	private static final String	STAUS_OK	= "200";
	public static final String	ERROR		= "-1";

	/**
	 * 响应编码
	 * 200成功
	 */
	public String				status;

	/**
	 * 响应消息
	 * 失败才有
	 */
	public String				message;

	/**
	 * 响应数据
	 */
	public Object				data;

	public Response(Object data) {
		this.data = data;
		this.status = STAUS_OK;
	}

	public Response(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
