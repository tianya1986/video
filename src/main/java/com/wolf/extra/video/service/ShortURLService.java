package com.wolf.extra.video.service;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.entity.ShortURL;

public interface ShortURLService {

    public ShortURL getShortURL(String url) throws VideoException;
}
