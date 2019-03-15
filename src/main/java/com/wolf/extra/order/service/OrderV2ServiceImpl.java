package com.wolf.extra.order.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.common.utils.DateUtil;
import com.wolf.extra.order.dao.OrderV2Dao;
import com.wolf.extra.order.helper.OrderHelper;
import com.wolf.extra.order.model.OrderV2;
import com.wolf.extra.video.VideoException;
import com.wolf.paging.Result;

@Service("orderV2Service")
public class OrderV2ServiceImpl implements OrderV2Service {

	@Override
	public OrderV2 load(String orderId) throws VideoException {
		return orderDao.load(orderId);
	}

	@Override
	public OrderV2 create(String uid,
			String platform,
			String videoId,
			String ip) throws VideoException {
		OrderV2 order = new OrderV2();
		String orderNumber = OrderHelper.makeOrderNumber();
		String createTime = DateUtil.convert2String(new Date(), DateUtil.ORACLE_DATETIME_FORMAT);
		String orderId = OrderHelper.makeOrderId();

		order.setUid(uid);
		order.setPlatform(platform);
		order.setOrderId(orderId); // 订单 UUID
		order.setVideoId(videoId); // 订单视频
		order.setOrderNumber(orderNumber); // 订单编号
		order.setStatus(OrderV2.Status.UN_PAID); // 订单状态：未支付
		order.setCreateTime(createTime); // 订单创建时间
		order.setIp(ip); // 用户ip
		return orderDao.create(order);
	}

	/**
	 * 完成订单
	 * @param orderId
	 * @param orderNumberPayment 订单号
	 * @param payType 分类 =1 支付宝 =2财付通  =3 微信
	 * @param price 金额
	 * @param appid APPID
	 * @return
	 * @throws VideoException
	 */
	@Override
	public OrderV2 complete(String orderId,
			String platform,
			String orderNumberPayment,
			String payType,
			int price) throws VideoException {
		OrderV2 order = load(orderId);
		if (order == null) {
			logger.error(TAG, "Can not found order, orderId = " + orderId);
			throw new VideoException("Can not found order, orderId = " + orderId);
		}

		String completeTime = DateUtil.convert2String(new Date(), DateUtil.ORACLE_DATETIME_FORMAT);

		order.setPlatform(platform);
		order.setOrderNumberPayment(orderNumberPayment);
		order.setCompleteTime(completeTime);
		order.setPrice(price); // 价格
		order.setPayType(payType);
		order.setStatus(OrderV2.Status.PAID); // 已支付
		return orderDao.update(order);
	}

	@Override
	public Result<OrderV2> query(String uid,
			String videoId,
			String status,
			String platform,
			long expireTime) throws VideoException {
		return orderDao.query(uid, videoId, status, platform, expireTime);
	}

	@Override
	public OrderV2 validateCode(String videoId,
			String code) throws VideoException {
		return null;
	}

	private Logger				logger	= LoggerFactory.getLogger(OrderV2ServiceImpl.class);
	private final static String	TAG		= "OrderController";

	@Autowired
	private OrderV2Dao			orderDao;

}
