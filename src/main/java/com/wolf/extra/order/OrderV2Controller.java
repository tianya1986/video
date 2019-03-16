package com.wolf.extra.order;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.Response;
import com.wolf.common.utils.NetworkUtil;
import com.wolf.cs.ContentException;
import com.wolf.cs.entity.Dentry;
import com.wolf.cs.service.ContentService;
import com.wolf.extra.order.helper.OrderHelper;
import com.wolf.extra.order.helper.ResponseHelper;
import com.wolf.extra.order.model.OrderV2;
import com.wolf.extra.order.payjs.SignUtil;
import com.wolf.extra.order.resp.MakeOrderBean;
import com.wolf.extra.order.service.OrderV2Service;
import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Video;
import com.wolf.extra.video.service.VideoService;
import com.wolf.paging.Result;

@RestController
@RequestMapping("/v2/order")
public class OrderV2Controller {

	@RequestMapping(value = "/{orderId}")
	public Response getOrder(@PathVariable("orderId") String orderId) {
		logger.info(TAG + " Get order info execute, order id = " + orderId);
		try {
			OrderV2 order = orderV2Service.load(orderId);
			if (order == null) {
				return new Response(Response.ERROR, "没有找到订单！");
			}

			if (!OrderV2.Status.PAID.equals(order.getStatus())) {
				// 订单未完成支付
				return new Response(Response.ERROR, "订单未完成！");
			}

			// 判断订单是否过期
			boolean isExpire = OrderHelper.isExpire(order);
			if (isExpire) {
				logger.info(TAG + " Get order info execute, is expire = " + isExpire);
				return new Response(Response.ERROR, "支付过期！");
			}

			Video video = videoService.load(order.getVideoId());
			if (video == null) {
				logger.info(TAG + " Get order info execute, video not exists, videoId = " + order.getVideoId());
				return new Response(Response.ERROR, "找不到视频");
			}

			Dentry denty = contentService.load(video.getDentryId());
			if (denty == null) {
				logger.info(TAG + " Get order info execute, video not exists, videoId = " + order.getVideoId());
				return new Response(Response.ERROR, "找不到视频");
			}

			video.setDentry(denty);
			return new Response(video);

		} catch (VideoException e) {
			e.printStackTrace();
			logger.error(TAG + " Create order error, " + e);
			return new Response(Response.ERROR, "系统异常");
		} catch (ContentException e) {
			e.printStackTrace();
			logger.error(TAG + " Create order error, " + e);
			return new Response(Response.ERROR, "系统异常");
		}
	}

	/**
	 * 用户发起支付请求
	 * 1. 创建订单
	 * 2. 如果有旧订单，验证旧订单是否已支付且在有效期内
	 * 3. 旧订单过期，重新生成
	 * @param request
	 * @param videoId
	 * @param orderId
	 * @return 
	 * 可以播放的话，返回信息中有视频信息
	 */
	@RequestMapping(value = "/create")
	public Response makeOrder(HttpServletRequest request,
			@RequestParam(name = "videoId", required = true) String videoId,
			@RequestParam(name = "host", required = false) String host,
			@RequestParam(name = "uid", required = false) String uid) {
		String userIpAddress = NetworkUtil.getIpAddress(request);

		logger.info(TAG + " Make order, videoId = " + videoId);
		logger.info(TAG + " Make order, user ip = " + userIpAddress); // 用户ip
		logger.info(TAG + " Make order, host = " + host); // host
		logger.info(TAG + " Make order, uid = " + uid); // 用户id

		try {
			Video video = videoService.load(videoId);
			if (video == null) {
				// 提示：找不到您要播放的视频。
				return ResponseHelper.errorVideoNotFound();
			}

			MakeOrderBean data = new MakeOrderBean();

			// 查询用户有效期内，且已支付的订单
			long expireTime = OrderConfig.getExpireTime();
			Result<OrderV2> paidOrders = orderV2Service.query(uid, videoId, OrderV2.Status.PAID, OrderConfig.Platform.PAYJS, expireTime);
			if (paidOrders != null && paidOrders.getCount() > 0) {
				// 已支付且在有效期内的订单
				logger.info(TAG + " Make order, Order old,  paid..");
				OrderV2 order = paidOrders.getItems().get(0);
				data.setOrder(order);
			} else {
				// 查询未支付的订单
				Result<OrderV2> unPaidOrders = orderV2Service.query(uid, videoId, OrderV2.Status.UN_PAID, OrderConfig.Platform.PAYJS, 0);
				if (unPaidOrders != null && unPaidOrders.getCount() > 0) {
					logger.info(TAG + " Make order, Order old, un paid ");
					OrderV2 order = unPaidOrders.getItems().get(0);
					data.setOrder(order);
				} else {
					// 创建订单
					logger.info(TAG + " Make order, Create order.");
					OrderV2 order = orderV2Service.create(uid, OrderConfig.Platform.PAYJS, videoId, userIpAddress);
					data.setOrder(order);
				}
			}

			OrderV2 order = data.getOrder();
			String payUrl = SignUtil.getPayUrl(order.getOrderId(), (int) (video.getPrice() * 100), order.getOrderNumber(), "视频打赏", host);
			data.setPayUrl(payUrl);
			data.setOrder(order);

			logger.info(TAG + " Make order, Order.orderId = " + order.getOrderId());
			logger.info(TAG + " Make order, Order.orderNumber = " + order.getOrderNumber());
			logger.info(TAG + " Make order, Order.status = " + order.getStatus());
			logger.info(TAG + " Make order, payUrl = " + data.getPayUrl());

			return new Response(data);
		} catch (VideoException e) {
			logger.error(TAG + " Create order error, " + e);
			return new Response(Response.ERROR, e.getMessage());
		}
	}

	/**
	 * 查询订单是否完成支付
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{orderId}/validate", method = RequestMethod.POST)
	public Response validateOrder(HttpServletRequest request,
			@PathVariable("orderId") String orderId) {
		try {
			OrderV2 order = orderV2Service.load(orderId);
			if (order == null) {
				return new Response(Response.ERROR, "找不到订单");
			}
			if (OrderV2.Status.PAID.equals(order.getStatus())) {
				// 完成支付
				return new Response("");
			}
		} catch (VideoException e) {
			e.printStackTrace();
			logger.error(TAG + " Order validated, load order error, " + e);
			return new Response(Response.ERROR, "load order error");
		}

		return new Response(Response.ERROR, "未支付");
	}

	private Logger				logger	= LoggerFactory.getLogger(OrderV2Controller.class);
	private final static String	TAG		= "OrderV2Controller";

	@Autowired
	private OrderV2Service		orderV2Service;											// 订单服务
	@Autowired
	private VideoService		videoService;												// 视频服务

	@Autowired
	private ContentService		contentService;
}
