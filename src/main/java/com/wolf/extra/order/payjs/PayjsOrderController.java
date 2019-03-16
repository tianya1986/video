package com.wolf.extra.order.payjs;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.Response;
import com.wolf.extra.order.OrderConfig;
import com.wolf.extra.order.service.OrderV2Service;
import com.wolf.extra.video.VideoException;

@RestController
@RequestMapping("/v2/order/payjs")
public class PayjsOrderController {

	/**
	 * 接收支付回调接口，完成订单
	 * 更新订单信息
	 * @param request
	 * @param code  1：支付成功
	 * @param price 金额。单位：分
	 * @param orderId 自己的订单号
	 * @param orderNumberPayjs PAYJS 订单号
	 * @param transactionId  微信用户手机显示订单号
	 * @param timeEnd 支付成功时间
	 * @param mchid PAYJS 商户号
	 * @return 不返回
	 */
	@RequestMapping(value = "/complete")
	public String complete(HttpServletRequest request,
			@RequestParam(value = "return_code", required = false) int code,
			@RequestParam(value = "total_fee", required = false) int price,
			@RequestParam(value = "out_trade_no", required = false) String orderId,
			@RequestParam(value = "payjs_order_id", required = false) String orderNumberPayjs,
			@RequestParam(value = "transaction_id", required = false) String transactionId,
			@RequestParam(value = "time_end", required = false) String timeEnd,
			@RequestParam(value = "mchid", required = false) String mchid) {
		logger.info(TAG + " Complete order, Order.orderId = " + orderId);
		logger.info(TAG + " Complete order, Order.orderNumberPayment = " + transactionId);
		logger.info(TAG + " Complete order, Order.key = " + price);
		logger.info(TAG + " Complete order, Order.price = " + price);
		logger.info(TAG + " Complete order, Order.appid = " + mchid);
		try {

			String payType = OrderConfig.PayType.WEIXIN; // payjs只有微信支付
			orderV2Service.complete(orderId, OrderConfig.Platform.PAYJS, transactionId, payType, price);
		} catch (VideoException e) {
			e.printStackTrace();
			logger.error(TAG + " Order callback error, " + e);
			return Response.ERROR;
		}
		return "200";
	}

	private Logger				logger	= LoggerFactory.getLogger(PayjsOrderController.class);
	private final static String	TAG		= "PayjsOrderController";

	@Autowired
	private OrderV2Service		orderV2Service;											// 订单服务

}
