package com.jishang.bimeng.entity.dt.nw;

import java.io.Serializable;

public class Dt_newptEntity implements Serializable{
	private String totalCount;
	private int defaultPageSize;
	
	
	
	
	
	
	
	
	
	public Dt_newptEntity() {
		super();
	}
	public Dt_newptEntity(String totalCount, int defaultPageSize) {
		super();
		this.totalCount = totalCount;
		this.defaultPageSize = defaultPageSize;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	@Override
	public String toString() {
		return "Dt_newptEntity [totalCount=" + totalCount
				+ ", defaultPageSize=" + defaultPageSize + "]";
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
}
