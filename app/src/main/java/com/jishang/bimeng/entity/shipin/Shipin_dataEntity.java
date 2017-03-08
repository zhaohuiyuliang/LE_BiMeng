package com.jishang.bimeng.entity.shipin;

import java.io.Serializable;

public class Shipin_dataEntity implements Serializable{
	private String id;
	private String title;
	private String keyword;
	private String description;
	private String vid_thumb;
	private String vid_https;
	private String vid_content;
	private String created_at;
	private String updated_at;
	private String v_status;
	
	
	
	
	public Shipin_dataEntity() {
		super();
	}
	public Shipin_dataEntity(String id, String title, String keyword,
			String description, String vid_thumb, String vid_https,
			String vid_content, String created_at, String updated_at,
			String v_status) {
		super();
		this.id = id;
		this.title = title;
		this.keyword = keyword;
		this.description = description;
		this.vid_thumb = vid_thumb;
		this.vid_https = vid_https;
		this.vid_content = vid_content;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.v_status = v_status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVid_thumb() {
		return vid_thumb;
	}
	public void setVid_thumb(String vid_thumb) {
		this.vid_thumb = vid_thumb;
	}
	public String getVid_https() {
		return vid_https;
	}
	public void setVid_https(String vid_https) {
		this.vid_https = vid_https;
	}
	public String getVid_content() {
		return vid_content;
	}
	public void setVid_content(String vid_content) {
		this.vid_content = vid_content;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getV_status() {
		return v_status;
	}
	public void setV_status(String v_status) {
		this.v_status = v_status;
	}
	@Override
	public String toString() {
		return "Shipin_dataEntity [id=" + id + ", title=" + title
				+ ", keyword=" + keyword + ", description=" + description
				+ ", vid_thumb=" + vid_thumb + ", vid_https=" + vid_https
				+ ", vid_content=" + vid_content + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + ", v_status=" + v_status + "]";
	}

	
	
	
}
