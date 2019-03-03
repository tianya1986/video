package com.wolf.extra.video.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService; // 订单服务

	/**
	 * 订单接收
	 * @param request
	 * @param orderNumber  订单号
	 * @param key KEY验证
	 * @param pay 分类 =1 支付宝 =2财付通  =3 微信
	 * @param price 金额
	 * @param appid 网址APPID
	 * @return
	 */
	@RequestMapping(value = "/save")
	public void order(HttpServletRequest request,
			@RequestParam(value = "ddh", required = false) String orderNumber,
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "pay", required = false) String pay,
			@RequestParam(value = "PayJe", required = false) String price,
			@RequestParam(value = "appid", required = false) String appid) {
		System.out.println("========================================= Order save =========================================");
		System.out.println("========================================= key:         " + key);
		System.out.println("========================================= orderNumber: " + orderNumber);
		System.out.println("========================================= pay:         " + pay);
		System.out.println("========================================= price:       " + price);
		System.out.println("========================================= appid:       " + appid);
		if ("31093750".equals(key)) {
			
		}
		try {
			orderService.save(orderNumber, key, pay, price, appid);
		} catch (VideoException e) {
			System.out.println("=========================================Order save error. ");
			e.printStackTrace();
		}
	}

}
