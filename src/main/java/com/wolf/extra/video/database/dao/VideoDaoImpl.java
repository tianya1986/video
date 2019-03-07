package com.wolf.extra.video.database.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.wolf.common.utils.StringUtil;
import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Video;
import com.wolf.paging.PagingUtil;
import com.wolf.paging.Result;

@Component
public class VideoDaoImpl implements VideoDao {

    /**
     * mongo访问类
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * <p>Description:   保存视频           </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: Administrator   </p>
     * @param video
     */
    @Override
    public void save(Video video) throws VideoException {
        mongoTemplate.save(video);
    }

    @Override
    public boolean delete(String videoId) throws VideoException {
        Video video = load(videoId);
        if (video != null) {
            DeleteResult result = mongoTemplate.remove(video);
            if (result.getDeletedCount() == 1) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * <p>Description:  加载视频详情            </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @param videoId 视频id
     * @return
     * @throws VideoException
     */
    @Override
    public Video load(String videoId) throws VideoException {
        Query query = new Query(Criteria.where("videoId").is(videoId));
        List<Video> list = mongoTemplate.find(query, Video.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * <p>Description: 更新视频       </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @param video
     * @return
     * @throws VideoException
     */
    @Override
    public Video update(Video video) throws VideoException {
        if (video == null || StringUtil.isEmpty(video.getVideoId())) {
            throw new VideoException("");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("videoId").is(video.getVideoId()));
        Update update = Update.update("name", video.getName()); // 视频名称
        update.set("price", video.getPrice()); // 价格
        update.set("shortURL", video.getShortURL()); // 视频短地址
        update.set("status", video.getStatus()); // 更新视频状态
        update.set("domain", video.getDomain()); // 更新域名
        UpdateResult result = mongoTemplate.updateFirst(query, update, Video.class);
        if (result.getModifiedCount() == 1) {
            return video;
        }
        return null;
    }

    /**
     * <p>Description:  查询视频  </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: Administrator   </p>
     * @param offset 查询起始位置
     * @param limit 查询数量
     * @param status 视频状态
     * @return
     * @throws VideoException
     */
    @Override
    public Result<Video> query(int offset, int limit, String status) throws VideoException {
        // 创建排序模板Sort
        int page = PagingUtil.getPage(offset, limit);
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");

        // 创建分页模板Pageable
        Pageable pageable = PageRequest.of(page, limit, sort);

        // 创建查询条件对象
        Query query = new Query();

        // 设置查询条件
        if (!StringUtil.isEmpty(status)) {
            Criteria criteria = Criteria.where("status").is(status);
            query.addCriteria(criteria);
        }

        // 查询总数
        long count = mongoTemplate.count(query, Video.class);
        List<Video> items = mongoTemplate.find(query.with(pageable), Video.class);

        return new Result<Video>(count, items);
    }
}
