package com.wolf.extra.video.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.extra.video.Status;
import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.dao.VideoDao;
import com.wolf.extra.video.database.entity.Video;
import com.wolf.paging.Result;

/**
 * 视频服务类
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Override
    public void save(Video video) throws VideoException {
        videoDao.save(video);
    }

    @Override
    public Video load(String videoId) throws VideoException {
        return videoDao.load(videoId);
    }

    @Override
    public Result<Video> query(int offset, int limit, String status) throws VideoException {
        return videoDao.query(offset, limit, status);
    }

    @Override
    public Video onSale(String videoId, String shortURL, float price, String domain) throws VideoException {
        Video video = load(videoId);
        if (video != null) {
            video.setShortURL(shortURL);
            video.setPrice(price);
            video.setDomain(domain);
            video.setStatus(Status.ON_SALE);
            video = videoDao.update(video);
        }
        return video;
    }

    @Override
    public Video onSaleFree(String videoId, String shortURL) throws VideoException {
        Video video = load(videoId);
        if (video != null) {
            video.setShortURL(shortURL);
            video.setPrice(0f);
            video.setStatus(Status.ON_FREE);
            video = videoDao.update(video);
        }
        return video;
    }

    @Override
    public Video offSale(String videoId) throws VideoException {
        Video video = load(videoId);
        if (video != null) {
            video.setShortURL("");
            video.setPrice(0f);
            video.setStatus(Status.OFF_SALE);
            video = videoDao.update(video);
        }
        return video;
    }
}
