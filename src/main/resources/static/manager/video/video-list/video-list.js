(function a() {
	function init() {

	}

	$.get("/video/list", function(data, status) {
		var $tbody = $("#simple-table > tbody");
		var items = data.items;
		var html = "";
		if (data != null) {
			for (var i = 0, len = items.length; i < len; i++) {
				var $tr = $("<tr>");
				createItem(items[i], $tr);
				$tbody.append($tr);
			}
		}
		
		createPaginator(data);

		function createItem(video, $tr) {
			$tr.empty();
			$tr.append("<td><a href='/manager/video/play/play.html?videoId=" + video.videoId + "' target='_blank'>" + video.name + "</a></td>"); // 名称
			$tr.append("<td class='center'>" + video.price + "元</td>"); // 价格
			$tr.append("<td class='center'>" + video.clickNumber + "</td>"); // 点击次数
			$tr.append("<td class='center'>" + getStatusName(video.status) + "</td>"); // 状态

			var shortURL = video.shortURL == null ? "" : video.shortURL;
			$tr.append("<td><a href='" + shortURL + "' target='_blank'>" + shortURL + "</a></td>"); // 短地址
			$tr.append("<td>" + dataFormat(video.createTime) + "</td>"); // 更新时间

			if (video.status == "0") {
				var $button = $("<button class='btn btn-xs btn-success'>上架</button>");
				$button.click(video, function(event) {
					var videoId = event.data.videoId;
					var button = $(this);
					$("#input_price").val(event.data.price);
					$("#dialog-confirm").removeClass('hide').dialog({
						resizable : false,
						width : '30%',
						modal : true,
						title : "视频上架",
						title_html : false,
						buttons : [ {
							html : "上架",
							"class" : "btn btn-primary btn-minier",
							click : function() {
								var json_data = {
									"price" : 0
								};
								json_data.price = $("#input_price").val();
								$.post("/video/" + videoId + "/action/onsale", json_data, function(video) {
									button.removeAttr("disabled")
									button.attr("disabled", "disabled");
									createItem(video, button.parent().parent());
								});
								$(this).dialog("close");
							}
						}, {
							html : "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 关闭",
							"class" : "btn btn-minier",
							click : function() {
								$(this).dialog("close");
							}
						} ]
					});

				});
				var $td = $("<td>")
				$td.append($button);
				$tr.append($td);
			} else if (video.status == "1" || video.status == "2") {
				var $button = $("<button class='btn btn-xs btn-danger'>下架</button>");
				$button.click(video.videoId, function(event) {
					var $this = $(this);
					$this.attr("disabled", "disabled");
					var videoId = event.data;
					$.post("/video/" + videoId + "/action/offsale", {}, function(video) {
						$this.removeAttr("disabled")
						createItem(video, $this.parent().parent());
					});
				});
				var $td = $("<td>")
				$td.append($button);
				$tr.append($td);
			}

		}
	});

	function dataFormat(time) {
		var date = new Date(time);
		var dateString = (date.getFullYear()) + "-" + (date.getMonth() + 1) + "-" + (date.getDate()) + " " + (date.getHours()) + ":" + (date.getMinutes()) + ":"
				+ (date.getSeconds());
		return dateString;
	}

	function getStatusName(status) {
		if (status == "0") {
			return "未上架";
		} else if (status == "1") {
			return "付费";
		} else if (status == "2") {
			return "免费";
		}
	}

	function createPaginator(result) {
		// 分页标签
		$('#pageLimit').bootstrapPaginator({
			currentPage : 1,// 当前的请求页面。
			totalPages : 20,// 一共多少页。
			size : "normal",// 应该是页眉的大小。
			bootstrapMajorVersion : 3,// bootstrap的版本要求。
			alignment : "right",
			numberOfPages : 5,// 一页列出多少数据。
			itemTexts : function(type, page, current) {// 如下的代码是将页眉显示的中文显示我们自定义的中文。
				switch (type) {
				case "first":
					return "首页";
				case "prev":
					return "上一页";
				case "next":
					return "下一页";
				case "last":
					return "末页";
				case "page":
					return page;
				}
			}
		});
	}

})();
