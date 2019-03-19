package com.wolf.video.service;

import com.wolf.video.dao.mongo.entity.Video;

/**
 * 
 * 视频服务接口
 * @author tianya
 *
 */
public interface VideoService {

	/**
	 * 保存视频
	 * @param dentryId
	 * @param videoName
	 * @return
	 */
	public Video save(String dentryId,
			String videoName);

	/**
	 * 视频播放量+1
	 * @param videoId 视频id
	 * @return true 成功
	 */
	public boolean updateVideoPlayNumber(String videoId);
}
