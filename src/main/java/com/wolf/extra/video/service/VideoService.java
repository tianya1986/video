package com.wolf.extra.video.service;

import com.wolf.extra.video.database.entity.Video;

import java.util.List;

public interface VideoService {

    List<Video> findAll();

    void save(Video video);
}
