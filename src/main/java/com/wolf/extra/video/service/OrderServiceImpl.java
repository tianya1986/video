package com.wolf.extra.video.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.common.utils.EnandeUtil;
import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.dao.OrderDao;
import com.wolf.extra.video.database.entity.Order;
import com.wolf.extra.video.response.VideoOrderResp;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	private final static String TAG = "OrderController";

	@Autowired
	private OrderDao orderDao;

	@Override
	public Order load(String orderId) throws VideoException {
		return orderDao.load(orderId);
	}

	@Override
	public Order create(String videoId, String orderNumber, String ipAddress,
			int status, String price) throws VideoException {
		return orderDao.create(videoId, orderNumber, ipAddress, status, price);
	}

	/**
	 * 更新订单
	 * @param orderNumber 订单号
	 * @param key KEY验证
	 * @param pay 分类 =1 支付宝 =2财付通  =3 微信
	 * @param price 金额
	 * @param appid 网址APPID
	 * @return
	 * @throws VideoException
	 */
	@Override
	public Order complete(String orderId, String orderNumberPayment,
			String key, String pay, String price, String appid)
			throws VideoException {
		return orderDao.complete(orderId, orderNumberPayment, key, pay, price,
				appid);
	}

	@Override
	public Order validateCode(String videoId, String code)
			throws VideoException {
		String[] codeInfo = VideoOrderResp.encrypt(code);
		if (codeInfo != null && codeInfo.length == 3) {
			logger.info(TAG + " Validate code, orderId = " + codeInfo[0]);
			logger.info(TAG + " Validate code, videoId = " + codeInfo[1]);
			logger.info(TAG + " Validate code, orderNumberPayment = "
					+ codeInfo[2]);

			if (videoId == null) {
				return null;
			}

			if (!videoId.equals(codeInfo[1])) {
				logger.info(TAG + " Validate code, 用户想看的视频与验证码code里的视频不一致");
				return null;
			}

			// 校验video与code里的订单是否一致
			Order order = load(codeInfo[0]);
			if (order == null) {
				logger.info(TAG + " Validate code, 找不到该订单id");
				return null;
			}

			if (order.getOrderNumberPayment() != null
					&& !order.getOrderNumberPayment().equals(codeInfo[2] + "")) {
				logger.info(TAG + " Validate code, 第三方平台的支付订单不一致。");
				return null;
			}

			long completeTime = order.getCompleteTime();
			if (EnandeUtil.isExpire(completeTime)) {
				// true 过期
				long nowTime = System.currentTimeMillis();
				logger.info(TAG + " Validate code, 支付完成时长（单位:分）："
						+ (nowTime - completeTime) / (1000 * 60));
				return null;
			}
			return order;
		}
		return null;
	}

}
