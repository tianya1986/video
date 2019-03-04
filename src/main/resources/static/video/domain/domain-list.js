(function a () {

	$.get("/domain/list", function (data , status) {
		var $tbody = $("#simple-table > tbody");
		var html = "";
		if (data != null) {
			for (var i = 0, len = data.length; i < len; i++) {
				var $tr = $("<tr>");
				createItem(i + 1, data[i], $tr);
				$tbody.append($tr);
			}
		}

		function createItem (index , domain , $tr) {
			$tr.empty();
			$tr.append("<td>" + index + "</td>"); // 编号
			$tr.append("<td class='center'>" + domain.domain + "</td>"); // 域名
			$tr.append("<td>" + dataFormat(domain.createTime) + "</td>"); // 创建时间

			// 创建时间
			var $button = $("<button class='btn btn-xs btn-danger'>删除</button>");
			$button.click(domain.domainId, function (event) {
				var domainId = event.data;
				var options = {
					url : '/domain/' + domainId,
					type : 'delete',
					dataType : 'text',
					data : $("#form-domain").serialize(),
					success : function (data) {
						if (data == "success") {
							alert("删除成功");
							window.location.reload();
						} else {
							alert("删除失败");
						}
					}
				};
				$.ajax(options);
			});
			var $td = $("<td>")
			$td.append($button);
			$tr.append($td);
		}
	});

	function dataFormat (time) {
		var date = new Date(time);
		var dateString = (date.getFullYear()) + "-" + (date.getMonth() + 1) + "-" + (date.getDate()) + " " + (date.getHours()) + ":" + (date.getMinutes()) + ":" + (date.getSeconds());
		return dateString;
	}

})();
