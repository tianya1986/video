(function a () {
	var videoId = getUrlParam("videoId");
	var code = $.cookie(videoId);
	var url = "/order/create/" + videoId;
	if (code != null) {
		url = url + "?code=" + code;
	}
	$.get(url, function (data , status) {
		if (data == null && data.video == null) {
			alert("请求失败，请退出再重试。");
			window.location.back();
			return;
		}

		// 免费视频
		if (data.order == null) {
			window.location.replace("./play-free.html?videoId=" + data.video.videoId);
		} else {

			if (data.order.status == "1") {
				window.location.replace("./play.html?orderId=" + data.order.orderId);
			} else {
				// 收费视频
				var tradeNumber = data.order.orderNumber;
				var host = escape(window.location.host);
				var url = "http://" + data.host + "/ldpay/alipay-v1.php?out_trade_no=" + tradeNumber 
					+ "&cny=" + data.video.price 
					+ "&pay=3&total_fee=" + data.video.price 
					+ "&orderId=" + data.order.orderId 
					+ "&host=" + host
					+ "&videoId=" + videoId;
				window.location.replace(url);
			}
		}
	});

	// 获取url中的参数
	function getUrlParam (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); // 匹配目标参数
		if (r != null) return unescape(r[2]);
		return null; // 返回参数值
	}
})();