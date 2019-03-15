package com.wolf.extra.order.dao;

import com.wolf.extra.order.model.OrderV2;
import com.wolf.extra.video.VideoException;
import com.wolf.paging.Result;

public interface OrderV2Dao {

	public OrderV2 load(String orderId) throws VideoException;

	/**
	 * 创建订单
	 * @param orderNumber
	 * @return
	 * @throws VideoException
	 */
	public OrderV2 create(OrderV2 order) throws VideoException;

	/**
	 * 完成
	 * @throws VideoException
	 */
	public OrderV2 update(OrderV2 order) throws VideoException;

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
}
