(function a () {
	var uid = $.cookie("uid");
	if (uid == null) {
		uid = guid();
		var date = new Date();
		date.setTime(date.getTime() + 1000 * 60 * 60 * 24 * 9999);
		$.cookie("uid", uid, {
			expires : date
		});
	}
	
	var videoId = getUrlParam("videoId");
	var host = window.location.host;
	var url = "/v2/order/create?videoId=" + videoId + "&host=" + host + "&uid=" + uid;
	$.get(url, function (result , status) {
		if (result == null || result.data.order == null) {
			alert("请求失败，请重试");
			return;
		}

		if (result.status != 200) {
			alert(result.message);
			return;
		}

		if (result.data == null) {
			alert("程序异常");
			return;
		}

		var order = result.data.order;
		if (order.status == "UN_PAID") {
			// 未支付
			var payUrl = result.data.payUrl;
			window.location.replace(payUrl);
		} else {
			// 已支付
			window.location.replace("./manager/video/v2/play-forward.html?orderId=" + order.orderId);
		}

	});

	// 获取url中的参数
	function getUrlParam (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); // 匹配目标参数
		if (r != null) return unescape(r[2]);
		return null; // 返回参数值
	}

	function S4 () {
		return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	}
	function guid () {
		return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
	}
})();