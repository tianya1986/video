package com.wolf.video.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.extra.video.database.entity.Video;
import com.wolf.extra.video.service.VideoService;
import com.wolf.paging.Result;

/**
 * 视频管理台 - 视频API
 *
 * @author tianya
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

	private static final Logger	logger	= LoggerFactory.getLogger(AdminController.class);
	private static final String	TAG		= "AdminVideoController";

	@Autowired
	private VideoService		videoService;												// 视频服务

	/**
	 * 分页查询视频
	 * 
	 * @param offset
	 * @param limit
	 * @param keyword
	 * @param orderBy 排序字段 例："status desc,playNumber asc"
	 * @param status 视频状态  
	 * @return
	 */
	@RequestMapping(value = "/video/list", method = RequestMethod.GET)
	public Result<Video> queryVideo(HttpServletRequest request,
			@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
			@RequestParam(name = "orderBy", required = false) String orderBy,
			@RequestParam(name = "keyword", required = false) String keyword) {
		logger.debug(TAG, "Query video list.");
		logger.debug(TAG, "Query video list, offset = " + offset);
		logger.debug(TAG, "Query video list, limit = " + limit);
		logger.debug(TAG, "Query video list, orderBy = " + offset);
		logger.debug(TAG, "Query video list, keyword = " + limit);

		Result<Video> result = null;
		return result;
	}
}
