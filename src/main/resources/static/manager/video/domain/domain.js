(function a () {

	$("#form-domain").bind("submit", function () {
		var domain = $("#domain").val();
		if (domain == "") {
			alert("请输入域名");
			return false;
		}

		var options = {
			url : '/domain/save',
			type : 'post',
			dataType : 'text',
			data : $("#form-domain").serialize(),
			success : function (data) {
				if (data != null) {
					alert("保存成功");
				} else {
					alert("保存失败");
				}
			}
		};
		$.ajax(options);
		return false;
	});

	$("#submit-domain").click(function () {
		$("#form-domain").submit();
	});
})();
