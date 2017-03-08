package com.jishang.bimeng.entity.dt;

import java.io.Serializable;

public class DtEntity implements Serializable{
	private String id;
	private String content;
	private String thumb_img;
	private String u_id;
	private String created_time;
	private String headimg;
	private String username;
	private String head_img;
	private String uc_id;
	
	
	
	
	
	public DtEntity() {
		super();
	}
	public DtEntity(String id, String content, String thumb_img, String u_id,
			String created_time, String headimg, String username,
			String head_img, String uc_id) {
		super();
		this.id = id;
		this.content = content;
		this.thumb_img = thumb_img;
		this.u_id = u_id;
		this.created_time = created_time;
		this.headimg = headimg;
		this.username = username;
		this.head_img = head_img;
		this.uc_id = uc_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getThumb_img() {
		return thumb_img;
	}
	public void setThumb_img(String thumb_img) {
		this.thumb_img = thumb_img;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public String getUc_id() {
		return uc_id;
	}
	public void setUc_id(String uc_id) {
		this.uc_id = uc_id;
	}
	@Override
	public String toString() {
		return "DtgsEntity [id=" + id + ", content=" + content + ", thumb_img="
				+ thumb_img + ", u_id=" + u_id + ", created_time="
				+ created_time + ", headimg=" + headimg + ", username="
				+ username + ", head_img=" + head_img + ", uc_id=" + uc_id
				+ "]";
	}
	
	
	
	
	
	
	

}
