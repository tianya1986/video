(function a() {
	$("#selectFile").click(function() {
		$("#uploadFile").click();
	});
	var fileBtn = $("input[name=file]");
	var processBar = $("#progressBar");
	var uploadBtn = $("#uploadSubmit");
	var canelBtn = $("input[name=cancelUpload]");
	
	var ot;// 上传开始时间
	var oloaded;// 已上传文件大小
	fileBtn.change(function() {
		var fileObj = fileBtn.get(0).files[0]; // js获取文件对象
		if (fileObj) {
			var fileSize = getSize(fileObj.size);
			$("#fileName").val(fileObj.name);
			$("#fileSize").val(fileSize);
			$("#fileType").val(fileObj.type);
			uploadBtn.attr('disabled', false);
		}
	});
	// 上传文件按钮点击的时候
	uploadBtn.click(function() {
		// 上传按钮禁用
		$(this).attr('disabled', true);
		var fileObj = fileBtn.get(0).files[0];
		if (fileObj == null) {
			alert("请选择文件");
			// 上传按钮禁用
			$(this).attr('disabled', false);
			return;
		}
		
		var fileType = $("#fileType").val();
		if(fileType == null || fileType == ""){
			alert("文件类型不能为空，你的文件没有拓展名");
			// 上传按钮禁用
			$(this).attr('disabled', false);
			return;
		}
		
		// 进度条归零
		setProgress(0);
		
		// 进度条显示
		showProgress();
		// 上传文件
		uploadFile();
	});
	function uploadFile() {
		var url = "/video/upload";
		var fileObj = fileBtn.get(0).files[0];
		// FormData 对象
		var form = new FormData();
		form.append('file', fileObj); // 文件对象
		// XMLHttpRequest 对象
		var xhr = new XMLHttpRequest();
		// true为异步处理
		xhr.open('post', url, true);
		// 上传开始执行方法
		xhr.onloadstart = function() {
			console.log('开始上传')
			ot = new Date().getTime(); // 设置上传开始时间
			oloaded = 0;// 已上传的文件大小为0
		};

		xhr.upload.addEventListener('progress', progressFunction, false);
		xhr.addEventListener("load", uploadComplete, false);
		xhr.addEventListener("error", uploadFailed, false);
		xhr.addEventListener("abort", uploadCanceled, false);
		xhr.send(form);

		function progressFunction(evt) {
			debugger;
			if (evt.lengthComputable) {
				var completePercent = Math.round(evt.loaded / evt.total * 100)
						+ '%';
				processBar.width(completePercent);
				processBar.text(completePercent);

				var time = $("#time");
				var nt = new Date().getTime(); // 获取当前时间
				var pertime = (nt - ot) / 1000; // 计算出上次调用该方法时到现在的时间差，单位为s
				ot = new Date().getTime(); // 重新赋值时间，用于下次计算

				var perload = evt.loaded - oloaded; // 计算该分段上传的文件大小，单位b
				oloaded = evt.loaded; // 重新赋值已上传文件大小

				// 上传速度计算
				var speed = perload / pertime;// 单位b/s
				var bspeed = speed;
				var units = 'b/s';// 单位名称
				if (speed / 1024 > 1) {
					speed = speed / 1024;
					units = 'k/s';
				}
				if (speed / 1024 > 1) {
					speed = speed / 1024;
					units = 'M/s';
				}
				speed = speed.toFixed(1);
				// 剩余时间
				var resttime = ((evt.total - evt.loaded) / bspeed).toFixed(1);
				$("#showInfo").html(speed + units + '，剩余时间：' + resttime + 's');
			}
		}

		// 上传成功后回调
		function uploadComplete(evt) {
			uploadBtn.attr('disabled', false);
			console.log('上传完成')
			alert("上传成功");
		}
		;

		// 上传失败回调
		function uploadFailed(evt) {
			console.log('上传失败' + evt.target.responseText);
			alert('上传失败' + evt.target.responseText);
		}

		// 终止上传
		function cancelUpload() {
			xhr.abort();
		}

		// 上传取消后回调
		function uploadCanceled(evt) {
			console.log('上传取消,上传被用户取消或者浏览器断开连接:' + evt.target.responseText);
		}

		canelBtn.click(function() {
			uploadBtn.attr('disabled', false);
			cancelUpload();
		})
	}
	function getSize(size) {
		var fileSize = '0KB';
		if (size > 1024 * 1024) {
			fileSize = (Math.round(size / (1024 * 1024))).toString() + 'MB';
		} else {
			fileSize = (Math.round(size / 1024)).toString() + 'KB';
		}
		return fileSize;
	}
	function setProgress(w) {
		processBar.width(w + '%');
	}
	function showProgress() {
		processBar.parent().show();
	}
	function hideProgress() {
		processBar.parent().hide();
	}
})();
