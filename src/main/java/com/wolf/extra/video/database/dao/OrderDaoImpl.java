package com.wolf.extra.video.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Order;

@Component
public class OrderDaoImpl implements OrderDao {

	/**
	 * mongo访问类
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

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
	public Order save(String orderNumber, String key, String pay, String price,
			String appid) throws VideoException {
		Order order = new Order();
		order.setOrderNumberPayment(orderNumber);
		order.setPayType(pay);
		order.setPrice(price);
		order.setCreateTime(System.currentTimeMillis());
		order.setStatus(1);
		return mongoTemplate.save(order);
	}

}
