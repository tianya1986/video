package com.wolf.extra.order.helper;

import com.wolf.Response;

public class ResponseHelper {

	private static final String STATUS_VIDEO_NOT_FOUND = "VIDEO/VIDEO_NOT_FOUND"; // 视频找不到

	private static final String MSG_VIDEO_NOT_FOUND = "视频找不到";

	/**
	 * 视频不存在
	 * @return
	 */
	public static Response errorVideoNotFound() {
		return new Response(STATUS_VIDEO_NOT_FOUND, MSG_VIDEO_NOT_FOUND);
	}
	
	
}
