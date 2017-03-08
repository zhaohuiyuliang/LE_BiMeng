
package com.jishang.bimeng.entity.chat.Fdlist;

import java.io.Serializable;

/**
 * 好友对象
 * 
 * @author wangliang Jul 15, 2016
 */
public class FriendEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -236946190537154545L;

    public String username;

    public String head_img;

    public String h_username;

    public FriendEntity() {
        super();
    }

    public FriendEntity(String username, String head_img, String h_username) {
        super();
        this.username = username;
        this.head_img = head_img;
        this.h_username = h_username;
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

    public String getH_username() {
        return h_username;
    }

    public void setH_username(String h_username) {
        this.h_username = h_username;
    }

    @Override
    public String toString() {
        return "DataEntity [username=" + username + ", head_img=" + head_img + ", h_username="
                + h_username + "]";
    }

}
