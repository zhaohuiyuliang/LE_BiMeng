
package com.jishang.bimeng.entity.dt.nw;

import java.io.Serializable;

/**
 * 个人昵称，头像
 * 
 * @author wangliang Jul 14, 2016
 */
public class Dt_new_data_usEntity implements Serializable {
    private String username;

    private String head_img;

    private String uid;

    public Dt_new_data_usEntity() {
        super();
    }

    public Dt_new_data_usEntity(String username, String head_img, String uid) {
        super();
        this.username = username;
        this.head_img = head_img;
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Dt_new_data_usEntity [username=" + username + ", head_img=" + head_img + ", uid="
                + uid + "]";
    }

}
