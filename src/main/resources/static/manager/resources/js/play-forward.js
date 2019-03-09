(function a () {
	var orderId = getUrlParam("orderId");
	var host = unescape(getUrlParam("host"));
	var orderNumber = getUrlParam("orderNumber");
	var videoId = getUrlParam("videoId");
	var url = "http://" + host + "/manager/play.html?orderId=" + orderId;

	var code = $.cookie(videoId);
	if(code == null){
		var date = new Date();
		date.setTime(date.getTime() + 1000 * 60 * 60 * 1);//
		var code = $.base64.encode(orderId + "|" + videoId + "|" + orderNumber);
		$.cookie(videoId, code, {
			expires : date
		});
	}
	
	window.location.replace(url);

	// 获取url中的参数
	function getUrlParam (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); // 匹配目标参数
		if (r != null) return unescape(r[2]);
		return null; // 返回参数值
	}
})();