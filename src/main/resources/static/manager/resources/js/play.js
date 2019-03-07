(function a () {
	var orderId = getUrlParam("orderId");
	var screenWidth = parseInt(window.screen.width);
	var screenHeight = parseInt(window.screen.height);
	console.log("Screen width : " + screenWidth);
	console.log("Screen height: " + screenHeight);
	$.get("/order/" + orderId, function (data , status) {

		var $container = $("#container");
		var $video = $("<video id='video-player' height='400' class='video-js' controls preload='auto'>")
		var $videoSource = $("<source type'video/mp4''>");

		if (data.order != null && data.video != null && data.video.dentry != null) {
			if (data.video.status == 1) {
				$.cookie(data.video.videoId, data.code, {
					expires : 1
				});
			}
			// $.cookie('the_cookie', 'the_value', { expires: 7 });
			var src = "/resources/video/" + data.video.dentry.dentryId + "_" + data.video.dentry.name;
			$videoSource.attr("src", src);
			$video.attr("width", screenWidth);
			$video.attr("height", screenHeight / 2);

			$video.append($videoSource);
			$container.append($video);

			videojs("video-player").ready(function () {
				var player = this;
				player.play();
			});
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