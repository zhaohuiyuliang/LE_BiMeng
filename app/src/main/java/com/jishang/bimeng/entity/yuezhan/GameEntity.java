package com.jishang.bimeng.entity.yuezhan;

import java.io.Serializable;
import java.util.List;

public class GameEntity implements Serializable{
	private int status;
	private String status_code;
	private List<PhotoEntity> game_list;
	
	
	
	
	
	public GameEntity() {
		super();
	}
	public GameEntity(int status, String status_code,
			List<PhotoEntity> game_list) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.game_list = game_list;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public List<PhotoEntity> getGame_list() {
		return game_list;
	}
	public void setGame_list(List<PhotoEntity> game_list) {
		this.game_list = game_list;
	}
	@Override
	public String toString() {
		return "GameEntity [status=" + status + ", status_code=" + status_code
				+ ", game_list=" + game_list + "]";
	}

	
	
	
	
}
