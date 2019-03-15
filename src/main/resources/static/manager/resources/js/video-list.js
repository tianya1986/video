(function a () {
	$.get("/domain/list", function (data , status) {
		var $select = $("#domainSelect");
		if (data != null) {
			for (var i = 0, len = data.length; i < len; i++) {
				var $option = $("<option>").val(data[i].domainId).text(data[i].domain);
				$select.append($option);
			}
		}
	});

	var LIMIT = 10;
	getVideoList(1, LIMIT);

	function getVideoList (page , limit) {
		var offset = (page - 1) * limit;
		$.get("/video/list?offset=" + offset + "&limit=" + limit, function (data , status) {
			var $tbody = $("#simple-table > tbody");
			$tbody.empty();
			var items = data.items;
			var html = "";
			if (items != null) {
				for (var i = 0, len = items.length; i < len; i++) {
					var $tr = $("<tr>");
					createItem(items[i], $tr);
					$tbody.append($tr);
				}
			}
			createPaginator(data, page);
		});
	}

	function createItem (video , $tr) {
		$tr.empty();
		$tr.append("<td>" + video.name + "</td>"); // 名称
		$tr.append("<td class='center'>" + video.price + "元</td>"); // 价格
		$tr.append("<td class='center'>" + video.clickNumber + "</td>"); // 点击次数
		$tr.append("<td class='center'>" + getStatusName(video.status) + "</td>"); // 状态

		var shortURL = video.shortURL == null ? null : video.shortURL;
		if(shortURL==null){
			$tr.append("<td></td>"); // 短地址
		}else{
			$tr.append("<td><a href='" + shortURL + "' target='_blank'>" + shortURL + "</a></td>"); // 短地址
		}
		
		var domain = video.domain == null ? "" : video.domain;
		$tr.append("<td class='center'>" + domain + "</td>"); // 域名
		$tr.append("<td class='center' >" + dataFormat(video.createTime) + "</td>"); // 更新时间

		if (video.status == "0") {
			var $button = $("<button class='btn btn-xs btn-success'>上架</button>");
			$button.click(video, function (event) {
				var videoId = event.data.videoId;
				var button = $(this);
				$("#dialog-confirm").removeClass('hide').dialog({
					resizable : false,
					width : '30%',
					modal : true,
					title : "视频上架",
					title_html : false,
					buttons : [ {
						html : "上架",
						"class" : "btn btn-primary btn-minier",
						click : function () {
							var json_data = {
								"price" : 0
							};
							json_data.price = $("#input_price").val();
							json_data.domainId = $("#domainSelect").val();
							json_data.platform = $("#platform").val();
							$.post("/video/" + videoId + "/action/onsale", json_data, function (video) {
								button.removeAttr("disabled")
								button.attr("disabled", "disabled");
								createItem(video, button.parent().parent());
							});
							$(this).dialog("close");
						}
					}, {
						html : "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 关闭",
						"class" : "btn btn-minier",
						click : function () {
							$(this).dialog("close");
						}
					} ]
				});
			});
			var $td = $("<td>")

			var $deleteButton = $("<button class='btn btn-xs btn-danger' style='margin-left:3px;'>删除</button>");
			$deleteButton.click(video, function (event) {
				var videoId = event.data.videoId;
				var $button = $(this);
				var message = confirm("确定删除？");
				if (message == true) {
					$.ajax({
					    url: '/video/' + videoId,
					    type: 'DELETE',
					    success: function(result) {
					    	if("success" == result){
					    		$button.parent().parent().empty();
					    	} else {
					    		alert("删除失败");
					    	}
					    }
					});
				} 
			});

			$td.append($button);
			$td.append($deleteButton);
			$tr.append($td);
		} else if (video.status == "1" || video.status == "2") {
			var $button = $("<button class='btn btn-xs btn-warning'>下架</button>");
			$button.click(video.videoId, function (event) {
				var message = confirm("确定下架？");
				if(message == true){
					var $this = $(this);
					$this.attr("disabled", "disabled");
					var videoId = event.data;
					$.post("/video/" + videoId + "/action/offsale", {}, function (video) {
						$this.removeAttr("disabled")
						createItem(video, $this.parent().parent());
					});
				}
			});
			var $td = $("<td>")
			$td.append($button);
			$tr.append($td);
		}
	}

	function dataFormat (time) {
		var date = new Date(time);
		var dateString = (date.getFullYear()) + "-" + (date.getMonth() + 1) + "-" + (date.getDate()) + " " + (date.getHours()) + ":" + (date.getMinutes()) + ":" + (date.getSeconds());
		return dateString;
	}

	function getStatusName (status) {
		if (status == "0") {
			return "未上架";
		} else if (status == "1") {
			return "付费";
		} else if (status == "2") { return "免费"; }
	}

	function createPaginator (result , page) {
		// 分页标签
		var count = result.count;
		var pages = Math.ceil(count / LIMIT);
		$('#pageLimit').bootstrapPaginator({
			currentPage : page,// 当前的请求页面。
			totalPages : pages,// 一共多少页。
			size : "normal",// 应该是页眉的大小。
			bootstrapMajorVersion : 3,// bootstrap的版本要求。
			alignment : "right",
			numberOfPages : 8,// 一页列出多少数据。
			itemTexts : function (type , page , current) {// 如下的代码是将页眉显示的中文显示我们自定义的中文。
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
			},
			onPageClicked : function (event , originalEvent , type , page) {
				getVideoList(page, LIMIT);
			}
		});
	}

})();
