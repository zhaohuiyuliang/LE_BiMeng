package com.jishang.bimeng.entity.shipin;

import java.io.Serializable;

public class Shipin_paEntity implements Serializable{
	
	private String totalCount;
	private int defaultPageSize;
	
	
	
	
	
	
	
	
	
	
	public Shipin_paEntity() {
		super();
	}
	public Shipin_paEntity(String totalCount, int defaultPageSize) {
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
		return "Shipin_paEntity [totalCount=" + totalCount
				+ ", defaultPageSize=" + defaultPageSize + "]";
	}

	
	
	
	
	
	
	
}
