package com.wolf.extra.video.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@Autowired
	private OrderService orderService; // 订单服务

	@Autowired
	private VideoService videoService; // 视频服务

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/{orderId}")
	public VideoOrderResp loadOrder(@PathVariable("orderId") String orderId) {
		System.out
				.println(" ========================================= loadOrder execute =========================================");
		VideoOrderResp result = new VideoOrderResp();
		try {
			Order order = orderService.load(orderId);
			if (order != null) {
				System.out.println(" ============= Order status "
						+ order.getStatus());
				System.out.println(" ============= Order price "
						+ order.getPrice());
				System.out.println(" ============= Order orderNumberPayment "
						+ order.getOrderNumberPayment());
				System.out.println(" ============= Order payType "
						+ order.getPayType());
				System.out.println(" ============= Order orderNumber "
						+ order.getOrderNumber());
				result.setOrder(order);
				Video video = videoService.load(order.getVideoId());
				if (video != null) {
					Dentry denty = contentService.load(video.getDentryId());
					video.setDentry(denty);
				}
				result.setVideo(video);
			}
		} catch (VideoException e) {
			e.printStackTrace();
		} catch (ContentException e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/create/{videoId}")
	public VideoOrderResp generateOrder(@PathVariable("videoId") String videoId) {
		System.out
				.println("========================================= Order create =========================================");
		System.out.println("========================================= videoId "
				+ videoId);
		VideoOrderResp orderResp = new VideoOrderResp();
		try {
			Video video = videoService.load(videoId);
			if (video != null) {
				orderResp.setVideo(video);
				String orderNumber = System.currentTimeMillis() + "";
				Random random = new Random();
				for (int i = 0; i < 3; i++) {
					orderNumber += random.nextInt(10);
				}
				Order order = orderService.create(videoId, orderNumber);
				orderResp.setOrder(order);
			}
		} catch (VideoException e) {
			e.printStackTrace();
		}
		return orderResp;
	}

	/**
	 * 订单接收
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
	public void order(
			HttpServletRequest request,
			@RequestParam(value = "text1", required = false) String orderId,
			@RequestParam(value = "ddh", required = false) String orderNumberPayment,
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "pay", required = false) String pay,
			@RequestParam(value = "PayJe", required = false) String price,
			@RequestParam(value = "appid", required = false) String appid) {
		System.out
				.println("========================================= Order save =========================================");
		System.out
				.println("========================================= orderId:         "
						+ orderId);
		System.out
				.println("========================================= key:         "
						+ key);
		System.out
				.println("========================================= orderNumberPayment: "
						+ orderNumberPayment);
		System.out
				.println("========================================= pay:         "
						+ pay);
		System.out
				.println("========================================= price:       "
						+ price);
		System.out
				.println("========================================= appid:       "
						+ appid);
		if ("31093750".equals(key)) {

		}
		try {
			orderService.update(orderId, orderNumberPayment, key, pay, price,
					appid);
		} catch (VideoException e) {
			System.out
					.println("=========================================Order save error. ");
			e.printStackTrace();
		}
	}

}
