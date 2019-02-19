(function a() {
	$.get("/video/list", function(data, status) {
		var $videoTable = $("#simple-table > tbody");
		var html = "";
		if (data != null) {
			for (var i = 0, len = data.length; i < len; i++) {
				html += "<tr>";
				html += "<td class=\"center\"><label class=\"pos-rel\"> <input type=\"checkbox\" class=\"ace\" /> <span class=\"lbl\"></span></label></td>";
				html += "<td><a href=\"#\">" + data[i].name + "</a></td>"; // 名称
				html += "<td>$45</td>"; // 价格
				html += "<td class=\"hidden-480\">3,330</td>"; // 点击次数
				html += "<td>Feb 12</td>"; // 更新时间
				html += "<td class=\"hidden-480\"><span class=\"label label-sm label-warning\">Expiring</span></td>"; // 状态
				html += "<th>操作</th>"; // 操作
			}
		}
		
		$videoTable.html(html);
	});
})();
