package com.jishang.bimeng.entity.dt.gs;

import java.util.List;

public class UserCotentEntity {
	private String uc_id;
	private String content;
	private String thumb_img;
	private String u_id;
	private String created_time;
	private UserPostEntity user_post;
	private List<ComentEntity> comments;
	private List<ClickEntiy> clicks;
	
	
	
	
	public UserCotentEntity() {
		super();
	}
	public UserCotentEntity(String uc_id, String content, String thumb_img,
			String u_id, String created_time, UserPostEntity user_post,
			List<ComentEntity> comments, List<ClickEntiy> clicks) {
		super();
		this.uc_id = uc_id;
		this.content = content;
		this.thumb_img = thumb_img;
		this.u_id = u_id;
		this.created_time = created_time;
		this.user_post = user_post;
		this.comments = comments;
		this.clicks = clicks;
	}
	public String getUc_id() {
		return uc_id;
	}
	public void setUc_id(String uc_id) {
		this.uc_id = uc_id;
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
	public UserPostEntity getUser_post() {
		return user_post;
	}
	public void setUser_post(UserPostEntity user_post) {
		this.user_post = user_post;
	}
	public List<ComentEntity> getComments() {
		return comments;
	}
	public void setComments(List<ComentEntity> comments) {
		this.comments = comments;
	}
	public List<ClickEntiy> getClicks() {
		return clicks;
	}
	public void setClicks(List<ClickEntiy> clicks) {
		this.clicks = clicks;
	}
	@Override
	public String toString() {
		return "UserCotentEntity [uc_id=" + uc_id + ", content=" + content
				+ ", thumb_img=" + thumb_img + ", u_id=" + u_id
				+ ", created_time=" + created_time + ", user_post=" + user_post
				+ ", comments=" + comments + ", clicks=" + clicks + "]";
	}
	
	
	

	
	
	
}
