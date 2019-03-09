package com.wolf.extra.video.controller;

import java.util.Map;
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
			if (order == null) {
				return result;
			}

			logger.info(TAG + " Get order info execute, Order.status = " + order.getStatus());
			logger.info(TAG + " Get order info execute, Order.price = " + order.getPrice());
			logger.info(TAG + " Get order info execute, Order.orderNumberPayment = " + order.getOrderNumberPayment());
			logger.info(TAG + " Get order info execute, Order.payType = " + order.getPayType());
			logger.info(TAG + " Get order info execute, Order.orderNumber = " + order.getOrderNumber());
			logger.info(TAG + " Get order info execute, Order.completeTime = " + order.getCompleteTime());
			logger.info(TAG + " Get order info execute, System time = " + System.currentTimeMillis());
			result.setOrder(order);

			Video video = videoService.load(order.getVideoId());
			if (video == null) {
				logger.info(TAG + " Get order info execute, video not exists ");
				return result;
			}

			Dentry denty = contentService.load(video.getDentryId());
			if (order.getStatus() == 0) {
				// 未完成支付，从支付服务器获取支付订单信息
				logger.info(TAG + " Get order info execute, order is not pay. ");
				Map<String, String> orderMap = new EnandeUtil().getOrderPayment(orderId);
				@SuppressWarnings("unused")
				String orderNumber = orderMap.get("text1"); // 自己的订单号
				String orderNumberPayment = orderMap.get("ddh"); // 支付平台的订单号
				String appid = orderMap.get("appid"); // appid
				String key = orderMap.get("key"); // key
				String price = orderMap.get("PayJe"); // 价格
				String pay = orderMap.get("pay"); // 支付渠道
				String status = orderMap.get("status"); // 是否完成支付
				if ("1".equals(status)) {
					// 订单完成支付
					logger.info(TAG + " Get order info execute, order pay success. ");
					order = orderService.complete(orderId, orderNumberPayment, key, pay, price, appid);
				} else {
					// 订单未支付
					logger.info(TAG + " Get order info execute, order not pay. ");
				}
			}

			if (order.getStatus() != 1) {
				// 订单未完成支付
				return result;
			}

			// 判断订单是否过期
			boolean isExpire = EnandeUtil.isExpire(order.getCompleteTime());
			logger.info(TAG + " Get order info execute, isExpire = " + isExpire);
			if (!isExpire) {
				// 未过期
				video.setDentry(denty);
				result.setVideo(video);
				result.generateCode(); // 生成唯一码
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
	public VideoOrderResp createOrder(HttpServletRequest request, @PathVariable("videoId") String videoId, @RequestParam(name = "code", required = false) String code) {
		String ipAddress = NetworkUtil.getIpAddress(request);
		logger.info(TAG + " Create order start, videoId = " + videoId);
		logger.info(TAG + " Create order start, Ip address = " + ipAddress);
		logger.info(TAG + " Create order start, code = " + code);
		VideoOrderResp orderResp = new VideoOrderResp();

		try {
			Video video = videoService.load(videoId);
			if (video == null) {
				// 提示：找不到您要播放的视频。
				return orderResp;
			}

			orderResp.setVideo(video);

			// 1. 免费视频
			if (com.wolf.extra.video.Status.ON_FREE.equals(video.getStatus())) {
				// 免费视频
				logger.info(TAG + " Create order start, video is Free. ");
				return orderResp;
			}

			// 2. 有支付校验码，验证支付是否超时
			if (code != null && code.length() > 0) {
				// 查询旧订单，验证支付是否超时。
				Order oldOrder = orderService.validateCode(videoId, code);
				// 支付未超时，设置价格为0，直接跳转到播放界面
				if (oldOrder != null) {
					// 判断订单是否过期
					boolean isExpire = EnandeUtil.isExpire(oldOrder.getCompleteTime());
					logger.info(TAG + " Create order start, old order is expire = " + isExpire);
					if (!isExpire) {
						// 未过期
						logger.info(TAG + " Create order start, Has old order. ");
						logger.info(TAG + " Create order start, Old order.orderNumber =  " + oldOrder.getOrderNumber());
						logger.info(TAG + " Create order start, Old order.orderNumberPayment =  " + oldOrder.getOrderNumberPayment());
						logger.info(TAG + " Create order start, Old order.price =  " + oldOrder.getPrice());
						logger.info(TAG + " Create order start, Old order pay success =  " + (oldOrder.getStatus() == 1));
						logger.info(TAG + " Create order start, Old order.userIp =  " + oldOrder.getUserIp());
						orderResp.setOrder(oldOrder);
						return orderResp;
					}
				}
			}

			// 生成订单
			String orderNumber = System.currentTimeMillis() + "";
			Random random = new Random();
			for (int i = 0; i < 3; i++) {
				orderNumber += random.nextInt(10);
			}

			Order order = orderService.create(videoId, orderNumber, ipAddress, 0, video.getPrice() + "");
			orderResp.setOrder(order);
			logger.info(TAG + " Create order start, Order.orderId = " + order.getOrderId());
			logger.info(TAG + " Create order start, Order.orderNumber = " + order.getOrderNumber());
			logger.info(TAG + " Create order start, Order.status = " + 0);
			logger.info(TAG + " Create order start, Video.price = " + video.getPrice());
			logger.info(TAG + " Create order start, Video.status = " + video.getStatus());
		} catch (VideoException e) {
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
	public void update(HttpServletRequest request, @RequestParam(value = "text1", required = false) String orderId, @RequestParam(value = "ddh", required = false) String orderNumberPayment,
			@RequestParam(value = "key", required = false) String key, @RequestParam(value = "pay", required = false) String pay, @RequestParam(value = "PayJe", required = false) String price,
			@RequestParam(value = "appid", required = false) String appid) {
		logger.info(TAG + " Update order start, Order.orderId = " + orderId);
		logger.info(TAG + " Update order start, Order.orderNumberPayment = " + orderNumberPayment);
		logger.info(TAG + " Update order start, Order.key = " + key);
		logger.info(TAG + " Update order start, Order.pay = " + pay);
		logger.info(TAG + " Update order start, Order.price = " + price);
		logger.info(TAG + " Update order start, Order.appid = " + appid);
		try {
			orderService.complete(orderId, orderNumberPayment, key, pay, price, appid);
		} catch (VideoException e) {
			e.printStackTrace();
			logger.error(TAG + " Create order error, " + e);
		}
	}

}
