package com.wolf.extra.video.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.Response;
import com.wolf.cs.service.ContentService;
import com.wolf.extra.video.service.VideoService;

/**
 * 视频 v2版本
 * 
 * 这里只做播放校验
 * @author tianya
 *
 */
@RestController
@RequestMapping("/v2/video")
public class VideoV2Controller {

	/**
	 * 播放视频
	 * 1. 免费视频，直接播放
	 * 2. 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/play/{code}")
	public Response playVideo(@PathVariable("code") String code) {
		
		return new Response(Response.ERROR, "Fail");
	}

	private Logger				logger	= LoggerFactory.getLogger(VideoV2Controller.class);
	private final static String	TAG		= "VideoV2Controller";

	@Autowired
	private ContentService		contentService;											// cs服务

	@Autowired
	private VideoService		videoService;												// 视频服务

}
