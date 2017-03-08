package com.jishang.bimeng.entity.dt.gs;

public class ComentEntity {
	private String ucmc_id;
	private String message;
	private String ucmp_id;
	private ComentUserEntity comment_users;
	
	
	
	
	public ComentEntity() {
		super();
	}
	public ComentEntity(String ucmc_id, String message, String ucmp_id,
			ComentUserEntity comment_users) {
		super();
		this.ucmc_id = ucmc_id;
		this.message = message;
		this.ucmp_id = ucmp_id;
		this.comment_users = comment_users;
	}
	public String getUcmc_id() {
		return ucmc_id;
	}
	public void setUcmc_id(String ucmc_id) {
		this.ucmc_id = ucmc_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUcmp_id() {
		return ucmp_id;
	}
	public void setUcmp_id(String ucmp_id) {
		this.ucmp_id = ucmp_id;
	}
	public ComentUserEntity getComment_users() {
		return comment_users;
	}
	public void setComment_users(ComentUserEntity comment_users) {
		this.comment_users = comment_users;
	}
	@Override
	public String toString() {
		return "ComentEntity [ucmc_id=" + ucmc_id + ", message=" + message
				+ ", ucmp_id=" + ucmp_id + ", comment_users=" + comment_users
				+ "]";
	}
	
	
	

}
