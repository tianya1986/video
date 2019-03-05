package com.wolf.extra.video.database.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.UpdateResult;
import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Order;
import com.wolf.extra.video.database.entity.Video;

@Component
public class OrderDaoImpl implements OrderDao {

	/**
	 * mongo访问类
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Order load(String orderId) throws VideoException {
		Query query = new Query(Criteria.where("orderId").is(orderId));
		List<Order> list = mongoTemplate.find(query, Order.class);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Order create(String videoId, String orderNumber, String ipAddress, int status)
			throws VideoException {
		Order order = new Order();
		order.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
		order.setOrderNumber(orderNumber);
		order.setCreateTime(System.currentTimeMillis());
		order.setVideoId(videoId);
		order.setUserIp(ipAddress);
		order.setStatus(status);
		return mongoTemplate.save(order);
	}

	/**
	 * 保存订单
	 * @param orderNumber 订单号
	 * @param key KEY验证
	 * @param pay 分类 =1 支付宝 =2财付通  =3 微信
	 * @param price 金额
	 * @param appid 网址APPID
	 * @return
	 * @throws VideoException
	 */
	@Override
	public Order complete(String orderId, String orderNumber, String key,
			String pay, String price, String appid) throws VideoException {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(orderId));
		Update update = Update.update("status", 1); // 订单状态
		update.set("price", price); // 价格
		update.set("completeTime", System.currentTimeMillis());
		UpdateResult result = mongoTemplate.updateFirst(query, update,
				Video.class);
		if (result.getModifiedCount() == 1) {
			return load(orderId);
		}
		return null;
	}

}
