package com.wolf.extra.video.database.dao;

import com.wolf.extra.video.database.entity.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoDaoImpl implements VideoDao {

    /**
     * mongo访问类
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveVideo(Video video) {
        mongoTemplate.save(video);
    }

    @Override
    public List<Video> findAll() {
        return mongoTemplate.findAll(Video.class);
    }
}
