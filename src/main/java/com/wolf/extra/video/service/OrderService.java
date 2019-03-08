package com.wolf.extra.video.service;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Order;

/**
 * 订单
 * @author tianya
 *
 */
public interface OrderService {

	public Order load(String orderId) throws VideoException;

	public Order create(String videoId, String orderNumber, String ipAddress,
			int status, String price) throws VideoException;

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
	public Order complete(String orderId, String orderNumber, String key,
			String pay, String price, String appid) throws VideoException;

	public Order validateCode(String videoId, String code)
			throws VideoException;

}
