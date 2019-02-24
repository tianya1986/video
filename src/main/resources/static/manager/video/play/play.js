(function a() {
	var videoId = getUrlParam("videoId");
	var screenWidth = parseInt(window.screen.width);
	var screenHeight = parseInt(window.screen.height);
	console.log("Screen width : " + screenWidth);
	console.log("Screen height: " + screenHeight);
	$.get("/video/" + videoId, function(data, status) {
		
		var $container = $("#container");
		var $video = $("<video id='video-player' height='400' class='video-js' controls preload='auto'>")
		var $videoSource = $("<source type'video/mp4''>");
		
		var src = "/resources/video/" + data.dentry.dentryId + "_"
				+ data.dentry.name;
		$videoSource.attr("src", src);
		$video.attr("width", screenWidth);
		$video.attr("height", screenHeight/2);
		
		$video.append($videoSource);
		$container.append($video);

		videojs("video-player").ready(function() {
			var player = this;
			player.play();
		});
	});
	
	//获取url中的参数
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); //匹配目标参数
		if (r != null)
			return unescape(r[2]);
		return null; //返回参数值
	}
})();