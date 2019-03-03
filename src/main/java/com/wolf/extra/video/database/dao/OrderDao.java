package com.wolf.extra.video.database.dao;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Order;

public interface OrderDao {

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
	public Order save(String orderNumber, String key, String pay, String price,
			String appid) throws VideoException;
}
