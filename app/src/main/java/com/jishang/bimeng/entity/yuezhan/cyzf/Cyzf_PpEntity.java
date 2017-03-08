package com.jishang.bimeng.entity.yuezhan.cyzf;

import java.io.Serializable;

public class Cyzf_PpEntity implements Serializable{
	private String id;
	private String join_peple;
	private String total_peple;
	private String user_join_level;
	
	
	
	
	public Cyzf_PpEntity() {
		super();
	}
	public Cyzf_PpEntity(String id, String join_peple, String total_peple,
			String user_join_level) {
		super();
		this.id = id;
		this.join_peple = join_peple;
		this.total_peple = total_peple;
		this.user_join_level = user_join_level;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJoin_peple() {
		return join_peple;
	}
	public void setJoin_peple(String join_peple) {
		this.join_peple = join_peple;
	}
	public String getTotal_peple() {
		return total_peple;
	}
	public void setTotal_peple(String total_peple) {
		this.total_peple = total_peple;
	}
	public String getUser_join_level() {
		return user_join_level;
	}
	public void setUser_join_level(String user_join_level) {
		this.user_join_level = user_join_level;
	}
	@Override
	public String toString() {
		return "Cyzf_PpEntity [id=" + id + ", join_peple=" + join_peple
				+ ", total_peple=" + total_peple + ", user_join_level="
				+ user_join_level + "]";
	}

	
	
	
	
}
