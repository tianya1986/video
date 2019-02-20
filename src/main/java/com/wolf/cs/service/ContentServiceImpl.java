package com.wolf.cs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.wolf.cs.ContentException;
import com.wolf.cs.Dentry;

/**
 * 内容服务方法
 * 
 * 1. 保存文件
 * 2. 读取文件信息
 */
public class ContentServiceImpl implements ContentService {

    /**
     * mongo访问类
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Dentry dentry) throws ContentException {
        mongoTemplate.save(dentry);
    }

    @Override
    public Dentry load(String dentryId) throws ContentException {
        return null;
    }

    @Override
    public List<Dentry> query() throws ContentException {
        return null;
    }
}
