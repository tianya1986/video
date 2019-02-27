package com.wolf.extra.video.database.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.VideoDomain;

@Component
public class VideoDomainDaoImpl implements VideoDomainDao {

	/**
	 * mongo访问类
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public VideoDomain load(String domainId) throws VideoException {
		Query query = new Query(Criteria.where("domainId").is(domainId));
		List<VideoDomain> list = mongoTemplate.find(query, VideoDomain.class);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 保存域名
	 * @param domain
	 * @return
	 * @throws VideoException
	 */
	@Override
	public VideoDomain save(VideoDomain domain) throws VideoException {
		return mongoTemplate.save(domain);
	}

	/**
	 * 删除域名
	 * @param domainId
	 * @throws VideoException
	 */
	@Override
	public void delete(String domainId) throws VideoException {
		Query query = new Query(Criteria.where("domainId").is(domainId));
		mongoTemplate.remove(query, VideoDomain.class);
	}

	/**
	 * 查询域名
	 * @return
	 * @throws VideoException
	 */
	@Override
	public List<VideoDomain> findAll() throws VideoException {
		return mongoTemplate.findAll(VideoDomain.class);
	}

}
