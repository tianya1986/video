package com.wolf.extra.video.database.dao;

import com.wolf.extra.video.database.entity.Video;

import java.util.List;

public interface VideoDao {

    void saveVideo(Video video);

    List<Video> findAll();
}
