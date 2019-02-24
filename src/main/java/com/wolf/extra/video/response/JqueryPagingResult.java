package com.wolf.extra.video.response;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * 服务器返回给页面
 * Jquery datatable
 * @author tianya
 *
 */
public class JqueryPagingResult<T> {

	private String draw;
	private long recordsTotal;

	private List<T> data;

	private String error;

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	

}
