package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;

public class YzList_ptEntity implements Serializable{
	private String pageParam;
	private String totalCount;
	private int defaultPageSize;
	
	
	
	
	
	
	
	
	public YzList_ptEntity() {
		super();
	}
	public YzList_ptEntity(String pageParam, String totalCount,
			int defaultPageSize) {
		super();
		this.pageParam = pageParam;
		this.totalCount = totalCount;
		this.defaultPageSize = defaultPageSize;
	}
	public String getPageParam() {
		return pageParam;
	}
	public void setPageParam(String pageParam) {
		this.pageParam = pageParam;
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
		return "YzList_ptEntity [pageParam=" + pageParam + ", totalCount="
				+ totalCount + ", defaultPageSize=" + defaultPageSize + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
