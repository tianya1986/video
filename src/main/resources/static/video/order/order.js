(function a () {
	var videoId = getUrlParam("videoId");
	$.get("/order/create/" + videoId, function (data , status) {
		if (data == null) {
			alert("视频不存在");
			return;
		}
		var price = data.video.price;
		var tradeNumber = data.order.orderNumber;
		var url = "http://" + data.host + "/ldpay/alipay.php?out_trade_no=" + tradeNumber + "&cny=" + price + "&pay=3&total_fee=" + price + "&orderId=" + data.order.orderId;
		console.log("================url " + url)
		 window.location.replace(url);
	});

	// 获取url中的参数
	function getUrlParam (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); // 匹配目标参数
		if (r != null) return unescape(r[2]);
		return null; // 返回参数值
	}
})();