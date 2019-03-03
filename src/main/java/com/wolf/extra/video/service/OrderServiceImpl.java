package com.wolf.extra.video.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.dao.OrderDao;
import com.wolf.extra.video.database.entity.Order;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	
    @Autowired
    private OrderDao orderDao;

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
		return orderDao.save(orderNumber, key, pay, price, appid);
	}

}
