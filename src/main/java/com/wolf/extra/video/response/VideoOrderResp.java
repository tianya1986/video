package com.wolf.extra.video.response;

import com.wolf.extra.video.database.entity.Order;
import com.wolf.extra.video.database.entity.Video;

public class VideoOrderResp {

	private Video video;

	private Order order;
	
	private String host = "www.hyf0w4o.top";

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	

}
