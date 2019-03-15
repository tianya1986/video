package com.wolf.extra.order.resp;

import com.wolf.extra.order.model.OrderV2;

/**
 * 创建订单响应数据
 * @author tianya
 */
public class MakeOrderBean {

	private OrderV2	order;

	public String	payUrl;

	public OrderV2 getOrder() {
		return order;
	}

	public void setOrder(OrderV2 order) {
		this.order = order;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

}
