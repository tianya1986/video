package com.wolf.uc.database.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wolf.uc.entity.UserInfo;

/**
 * 用户数据库操作类
 * @author tianya
 *
 */
@Component
public class AccountDaoImpl implements AccountDao {

	/**
	 * mongo访问类
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UserInfo load(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(id));
		List<UserInfo> result = mongoTemplate.find(query, UserInfo.class);
		if (result != null && result.size() == 1) {
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public UserInfo save(UserInfo user) {
		return mongoTemplate.save(user);
	}

}
