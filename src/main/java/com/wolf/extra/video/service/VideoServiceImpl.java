package com.wolf.extra.video.service;

import com.wolf.extra.video.database.dao.VideoDao;
import com.wolf.extra.video.database.entity.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("videoService")
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoDao videoDao;

	@Override
	public List<Video> findAll() {
		return videoDao.findAll();
	}

	@Override
	public void save(Video video) {
		videoDao.saveVideo(video);
	}
}
