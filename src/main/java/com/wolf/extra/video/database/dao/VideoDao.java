package com.wolf.extra.video.database.dao;

import paging.Result;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Video;

public interface VideoDao {

    /**
     * <p>Description:   保存视频           </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @param video
     */
    public void save(Video video) throws VideoException;

    /**
     * <p>Description:  加载视频详情            </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @param videoId 视频id
     * @return
     * @throws VideoException
     */
    public Video load(String videoId) throws VideoException;

    /**
     * <p>Description: 更新视频       </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: Administrator   </p>
     * @param video
     * @return
     * @throws VideoException
     */
    public Video update(Video video) throws VideoException;

    /**
     * <p>Description:  查询视频  </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @param offset 查询起始位置
     * @param limit 查询数量
     * @param status 视频状态
     * @return
     * @throws VideoException
     */
    public Result<Video> query(int offset, int limit, int status) throws VideoException;

}
