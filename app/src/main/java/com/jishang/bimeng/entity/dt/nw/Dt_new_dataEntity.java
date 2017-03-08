
package com.jishang.bimeng.entity.dt.nw;

import java.io.Serializable;
import java.util.List;

/**
 * 好友及我发的动态对象
 * 
 * @author wangliang Jul 14, 2016
 */
public class Dt_new_dataEntity implements Serializable {
    private String uc_id;

    private String content;

    private String thumb_img;

    private String u_id;

    private String created_time;

    private Dt_new_data_usEntity user_post;

    private List<Dt_new_data_cmEntity> comment_count;

    private List<Dt_new_data_clEntity> click_count;

    public Dt_new_dataEntity() {
        super();
    }

    public Dt_new_dataEntity(String uc_id, String content, String thumb_img, String u_id,
            String created_time, Dt_new_data_usEntity user_post,
            List<Dt_new_data_cmEntity> comment_count, List<Dt_new_data_clEntity> click_count) {
        super();
        this.uc_id = uc_id;
        this.content = content;
        this.thumb_img = thumb_img;
        this.u_id = u_id;
        this.created_time = created_time;
        this.user_post = user_post;
        this.comment_count = comment_count;
        this.click_count = click_count;
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

    public Dt_new_data_usEntity getUser_post() {
        return user_post;
    }

    public void setUser_post(Dt_new_data_usEntity user_post) {
        this.user_post = user_post;
    }

    public List<Dt_new_data_cmEntity> getComment_count() {
        return comment_count;
    }

    public void setComment_count(List<Dt_new_data_cmEntity> comment_count) {
        this.comment_count = comment_count;
    }

    public List<Dt_new_data_clEntity> getClick_count() {
        return click_count;
    }

    public void setClick_count(List<Dt_new_data_clEntity> click_count) {
        this.click_count = click_count;
    }

    @Override
    public String toString() {
        return "Dt_new_dataEntity [uc_id=" + uc_id + ", content=" + content + ", thumb_img="
                + thumb_img + ", u_id=" + u_id + ", created_time=" + created_time + ", user_post="
                + user_post + ", comment_count=" + comment_count + ", click_count=" + click_count
                + "]";
    }

}
