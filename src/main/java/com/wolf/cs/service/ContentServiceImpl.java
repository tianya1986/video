package com.wolf.cs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.wolf.cs.ContentException;
import com.wolf.cs.entity.Dentry;

/**
 * 内容服务方法
 * 
 * 1. 保存文件
 * 2. 读取文件信息
 */
@Service("contentSerivce")
public class ContentServiceImpl implements ContentService {

	/**
	 * mongo访问类
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Dentry save(Dentry dentry) throws ContentException {
		return mongoTemplate.save(dentry);
	}

	@Override
	public Dentry load(String dentryId) throws ContentException {
		Query query = new Query(Criteria.where("dentryId").is(dentryId));
		List<Dentry> list = mongoTemplate.find(query, Dentry.class);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Dentry> query() throws ContentException {
		return null;
	}
	
	@Override
	public void delete(String dentryId) throws ContentException {
		Query query = new Query(Criteria.where("dentryId").is(dentryId));
		mongoTemplate.remove(query, Dentry.class);
	}
}
