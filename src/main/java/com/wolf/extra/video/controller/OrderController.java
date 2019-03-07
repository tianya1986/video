package com.wolf.extra.video.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.common.utils.EnandeUtil;
import com.wolf.common.utils.NetworkUtil;
import com.wolf.cs.ContentException;
import com.wolf.cs.entity.Dentry;
import com.wolf.cs.service.ContentService;
import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Order;
import com.wolf.extra.video.database.entity.Video;
import com.wolf.extra.video.response.VideoOrderResp;
import com.wolf.extra.video.service.OrderService;
import com.wolf.extra.video.service.VideoService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private Logger logger = LoggerFactory.getLogger(OrderController.class);
	private final static String TAG = "OrderController";

	@Autowired
	private OrderService orderService; // 订单服务

	@Autowired
	private VideoService videoService; // 视频服务

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/{orderId}")
	public VideoOrderResp getOrder(@PathVariable("orderId") String orderId) {
		logger.info(TAG + " Get order info execute, order id = " + orderId);
		VideoOrderResp result = new VideoOrderResp();
		try {
			Order order = orderService.load(orderId);
			if (order != null) {
				logger.info(TAG + " Get order info execute, Order.status = "
						+ order.getStatus());
				logger.info(TAG + " Get order info execute, Order.price = "
						+ order.getPrice());
				logger.info(TAG
						+ " Get order info execute, Order.orderNumberPayment = "
						+ order.getOrderNumberPayment());
				logger.info(TAG + " Get order info execute, Order.payType = "
						+ order.getPayType());
				logger.info(TAG
						+ " Get order info execute, Order.orderNumber = "
						+ order.getOrderNumber());
				result.setOrder(order);
				boolean isExpire = EnandeUtil.isExpire(order.getCompleteTime());
				if (!isExpire) {
					// 过期
					Video video = videoService.load(order.getVideoId());
					if (video != null && order.getStatus() == 1) {
						// 需要完成支付
						Dentry denty = contentService.load(video.getDentryId());
						video.setDentry(denty);
					}
					result.setVideo(video);
					result.generateCode(); // 生成唯一码
				}
			}
		} catch (VideoException e) {
			e.printStackTrace();
			logger.error(TAG + " Create order error, " + e);
		} catch (ContentException e) {
			e.printStackTrace();
			logger.error(TAG + " Create order error, " + e);
		}
		return result;
	}

	@RequestMapping(value = "/create/{videoId}")
	public VideoOrderResp createOrder(HttpServletRequest request,
			@PathVariable("videoId") String videoId,
			@RequestParam(name = "code", required = false) String code) {
		String ipAddress = NetworkUtil.getIpAddress(request);
		logger.info(TAG + " Create order start, video = " + videoId);
		logger.info(TAG + " Create order start, Ip address = " + ipAddress);
		logger.info(TAG + " Create order start, code = " + code);

		VideoOrderResp orderResp = new VideoOrderResp();
		try {
			// 超时
			Video video = videoService.load(videoId);
			Order oldOrder = orderService.validateCode(videoId, code);

			logger.info(TAG + " Create order start, isExpire = "
					+ (oldOrder == null));
			if (video != null) {
				orderResp.setVideo(video);

				// 支付未超时，设置价格为0，直接跳转到播放界面
				if (oldOrder != null) {
					video.setPrice(0);
					orderResp.setOrder(oldOrder);
				} else {
					// 没有code，或者已支付超时，重新生成订单
					String orderNumber = System.currentTimeMillis() + "";
					Random random = new Random();
					for (int i = 0; i < 3; i++) {
						orderNumber += random.nextInt(10);
					}
					int status = 0;
					if (com.wolf.extra.video.Status.ON_FREE.equals(video
							.getStatus())) {
						status = 1; // 免费的视频，直接完成订单
					}
					Order order = orderService.create(videoId, orderNumber,
							ipAddress, status);
					logger.info(TAG + " Create order start, Order.orderId = "
							+ order.getOrderId());
					logger.info(TAG
							+ " Create order start, Order.orderNumber = "
							+ order.getOrderNumber());
					logger.info(TAG + " Create order start, Order.status = "
							+ status);
					logger.info(TAG + " Create order start, Video.price = "
							+ video.getPrice());
					logger.info(TAG + " Create order start, Video.status = "
							+ video.getStatus());
					orderResp.setOrder(order);
				}
			}
		} catch (VideoException e) {
			e.printStackTrace();
			logger.error(TAG + " Create order error, " + e);
		}
		return orderResp;
	}

	/**
	 * 完成订单
	 * @param request
	 * @param orderNumber  订单号
	 * @param orderNumberPayment 订单平台订单
	 * @param key KEY验证
	 * @param pay 分类 =1 支付宝 =2财付通  =3 微信
	 * @param price 金额
	 * @param appid 网址APPID
	 * @return
	 */
	@RequestMapping(value = "/update")
	public void update(
			HttpServletRequest request,
			@RequestParam(value = "text1", required = false) String orderId,
			@RequestParam(value = "ddh", required = false) String orderNumberPayment,
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "pay", required = false) String pay,
			@RequestParam(value = "PayJe", required = false) String price,
			@RequestParam(value = "appid", required = false) String appid) {
		logger.info(TAG + " Update order start, Order.orderId = " + orderId);
		logger.info(TAG + " Update order start, Order.orderNumberPayment = "
				+ orderNumberPayment);
		logger.info(TAG + " Update order start, Order.key = " + key);
		logger.info(TAG + " Update order start, Order.pay = " + pay);
		logger.info(TAG + " Update order start, Order.price = " + price);
		logger.info(TAG + " Update order start, Order.appid = " + appid);
		try {
			orderService.complete(orderId, orderNumberPayment, key, pay, price,
					appid);
		} catch (VideoException e) {
			e.printStackTrace();
			logger.error(TAG + " Create order error, " + e);
		}
	}

}
