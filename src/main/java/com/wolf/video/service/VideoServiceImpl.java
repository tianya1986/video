package com.wolf.video.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.video.dao.mongo.VideoMongoDao;
import com.wolf.video.dao.mongo.entity.Video;
import com.wolf.video.dao.mongo.entity.Video.Status;

/**
 * 视频服务类
 * 
 * 1. 视频播放量+1 {@link #updateVideoPlayNumber(String)}
 * @author tianya
 *
 */
@Service("videoV2Service")
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoMongoDao	videoDao;

	@Override
	public Video save(String dentryId,
			String videoName) {
		long createTime = System.currentTimeMillis();
		int price = 0;
		String status = Status.SALE_OFF; // 下架状态
		int payTotal = 0;
		String videoId = UUID.randomUUID().toString().replaceAll("-", "");

		Video video = new Video();
		video.setVideoId(videoId);
		video.setDentryId(dentryId);
		video.setVideoName(videoName);
		video.setPrice(price);
		video.setPayTotal(payTotal);
		video.setStatus(status);
		video.setCreateTime(createTime);

		return videoDao.save(video);
	}

	/**
	 * 视频播放量+1
	 * @param videoId 视频id
	 * @return true 成功
	 */
	@Override
	public boolean updateVideoPlayNumber(String videoId) {
		Video video = videoDao.load(videoId);
		if (video != null) {
			int payTotal = video.getPayTotal() + 1;

			Map<String, Object> param = new HashMap<>();
			param.put(Video.PAY_TOTAL, payTotal);
			return videoDao.update(videoId, param);
		}
		return false;
	}
}
