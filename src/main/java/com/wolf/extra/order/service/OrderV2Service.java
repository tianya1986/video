package com.wolf.extra.order.service;

import com.wolf.extra.order.model.OrderV2;
import com.wolf.extra.video.VideoException;
import com.wolf.paging.Result;

/**
 * 订单
 * @author tianya
 *
 */
public interface OrderV2Service {

	public OrderV2 load(String orderId) throws VideoException;

	/**
	 * 创建订单
	 * @param videoId
	 * @param ipAddress
	 * @return
	 * @throws VideoException
	 */
	public OrderV2 create(String uid, String platform, String videoId, String ipAddress) throws VideoException;

	/**
	 * 完成订单
	 * @param orderNumber 订单号
	 * @param platform 平台编码
	 * @param key KEY验证
	 * @param pay 分类 =1 支付宝 =2财付通  =3 微信
	 * @param price 金额
	 * @param appid 网址APPID
	 * @return
	 * @throws VideoException
	 */
	public OrderV2 complete(String orderId, String platform, String orderNumberPayment, String payType, int price) throws VideoException;
	
	/**
	 * 查询订单
	 * @param uid 用户id
	 * @param videoId 视频id
	 * @param status 订单状态
	 * @param platform 平台
	 * @return
	 * @throws VideoException
	 */
	public Result<OrderV2> query(String uid,
			String videoId,
			String status,
			String platform,
			long expireTime) throws VideoException;

	public OrderV2 validateCode(String videoId, String code) throws VideoException;

}
