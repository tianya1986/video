package com.wolf.mongodb;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public abstract class MgongoDao<T> {

	/**
	 * mongo访问类
	 */
	@Autowired
	private MongoTemplate	mongoTemplate;

	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	public T load(String id) {
		if (id == null) {
			return null;
		}

		String colunmId = getColumnId();
		Class<T> clazz = getEntityClass();

		Query query = new Query(Criteria.where(colunmId).is(id));
		List<T> list = mongoTemplate.find(query, clazz);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 保存数据
	 * @param t
	 * @return
	 */
	public T save(T t) {
		return mongoTemplate.save(t);
	}

	public boolean update(String id,
			Map<String, Object> param) {
		Class<T> clazz = getEntityClass();
		String columnId = getColumnId();

		Query query = new Query();
		query.addCriteria(Criteria.where(columnId).is(id));

		Update update = new Update();
		for (String key : param.keySet()) {
			Object value = param.get(key);
			update.set(key, value);
		}

		UpdateResult result = mongoTemplate.updateFirst(query, update, clazz);
		if (result.getModifiedCount() == 1) {
			return true;
		}
		return false;
	}

	public boolean delete(String id) {
		T object = load(id);
		if (object != null) {
			DeleteResult result = mongoTemplate.remove(object);
			if (result.getDeletedCount() == 1) {
				return true;
			}
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		ParameterizedType types = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) types.getActualTypeArguments()[0];
	}

	protected abstract String getColumnId();
}
