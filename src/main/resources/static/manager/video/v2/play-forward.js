(function a () {
	var orderId = getUrlParam("orderId");
	if (orderId == null || orderId == "") { return; }

	var total = 100;
	var count = 0;
	var time = setInterval(function () {
		var url = '/v2/order/' + orderId + '/validate';
		$.ajax({
			type : 'POST',
			url : url,
			success : function (data) {
				if (data.status == '200') {
					var url = "./play.html?orderId=" + orderId;
					window.location.replace(url);
					clearInterval(time);
				}
			}
		});
		count++;
		if (count > total) {
			clearInterval(time);
		}
	}, 1000);

	// 获取url中的参数
	function getUrlParam (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); // 匹配目标参数
		if (r != null) return unescape(r[2]);
		return null; // 返回参数值
	}

})();