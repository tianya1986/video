package com.wolf.extra.video.service;

import paging.Result;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Video;

/**
 * 视频服务类
 */
public interface VideoService {

    /**
     * <p>Description:   保存视频           </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @param video
     */
    void save(Video video) throws VideoException;

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
    public Result<Video> query(int offset, int limit, String status) throws VideoException;

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
     * <p>Description: 上架视频             </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: Administrator   </p>
     * @param videoId
     * @param shortURL
     * @param price
     * @return
     * @throws VideoException
     */
    public Video onSale(String videoId, String shortURL, float price) throws VideoException;

    /**
     * <p>Description: 下架视频             </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: Administrator   </p>
     * @param videoId
     * @return
     * @throws VideoException
     */
    public Video offSale(String videoId) throws VideoException;

    /**
     * 	
     * <p>Description:  上架免费视频            </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: Administrator   </p>
     * @param videoId
     * @param shortURL
     * @return
     * @throws VideoException
     */
    public Video onSaleFree(String videoId, String shortURL) throws VideoException;
}
