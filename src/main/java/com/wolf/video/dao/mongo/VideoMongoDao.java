package com.wolf.video.dao.mongo;

import org.springframework.stereotype.Component;

import com.wolf.mongodb.MgongoDao;
import com.wolf.video.dao.mongo.entity.Video;

/**
 * 视频 mongodb 操作类
 */
@Component
public class VideoMongoDao extends MgongoDao<Video> {

	@Override
	protected String getColumnId() {
		return Video.ID;
	}

}
