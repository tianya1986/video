package com.wolf.extra.order.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.UpdateResult;
import com.wolf.common.utils.StringUtil;
import com.wolf.extra.order.model.OrderV2;
import com.wolf.extra.video.VideoException;
import com.wolf.paging.Result;

/**
 * 新订单 mongodb操作类
 * 1. 创建订单
 * 2. 加载订单详情
 * 3. 更新订单
 * @author tianya
 *
 */
@Component
public class OrderV2DaoImpl implements OrderV2Dao {

	/**
	 * mongo访问类
	 */
	@Autowired
	private MongoTemplate	mongoTemplate;

	@Override
	public OrderV2 load(String orderId) throws VideoException {
		Query query = new Query(Criteria.where("orderId").is(orderId));
		List<OrderV2> list = mongoTemplate.find(query, OrderV2.class);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public OrderV2 create(OrderV2 order) throws VideoException {
		if (order == null) {
			logger.error(TAG, "OrderV2 to save must not be null!");
			throw new VideoException("OrderV2 to save must not be null!");
		}
		return mongoTemplate.save(order);
	}

	/**
	 * 更新订单信息
	 * @param order 必须是从数据库中加载出来的对象
	 */
	@Override
	public OrderV2 update(OrderV2 order) throws VideoException {
		if (order == null || order.get_id() == null) {
			logger.error(TAG, "Order is null.");
			throw new VideoException("订单不能为空");
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(order.getOrderId()));
		Update update = Update.update("status", order.getStatus()); // 订单状态
		update.set("price", order.getPrice()); // 价格
		update.set("completeTime", order.getCompleteTime());
		update.set("orderNumberPayment", order.getOrderNumberPayment());
		update.set("payType", order.getPayType());
		update.set("userIp", order.getIp());
		UpdateResult result = mongoTemplate.updateFirst(query, update, OrderV2.class);
		if (result.getModifiedCount() == 1) {
			return order;
		}
		return null;
	}

	@Override
	public Result<OrderV2> query(String uid,
			String videoId,
			String status,
			String platform,
			long expireTime) throws VideoException {

		// 创建查询条件对象
		Query query = new Query();

		// 设置查询条件
		if (!StringUtil.isEmpty(uid)) {
			Criteria criteria = Criteria.where("uid").is(uid);
			query.addCriteria(criteria);
		}
		if (!StringUtil.isEmpty(videoId)) {
			Criteria criteria = Criteria.where("videoId").is(videoId);
			query.addCriteria(criteria);
		}
		if (!StringUtil.isEmpty(status)) {
			Criteria criteria = Criteria.where("status").is(status);
			query.addCriteria(criteria);
		}
		if (!StringUtil.isEmpty(platform)) {
			Criteria criteria = Criteria.where("platform").is(platform);
			query.addCriteria(criteria);
		}

		if (expireTime > 0) {
			long minTime = System.currentTimeMillis() - expireTime;
			Criteria criteria = Criteria.where("completeTime").gt(minTime);
			query.addCriteria(criteria);
		}

		// 查询总数
		long count = mongoTemplate.count(query, OrderV2.class);
		List<OrderV2> items = mongoTemplate.find(query, OrderV2.class);

		return new Result<OrderV2>(count, items);
	}

	private Logger				logger	= LoggerFactory.getLogger(OrderV2DaoImpl.class);
	private final static String	TAG		= "OrderV2Dao";
}
