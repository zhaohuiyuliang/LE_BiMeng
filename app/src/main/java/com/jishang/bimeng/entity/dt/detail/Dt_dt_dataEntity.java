
package com.jishang.bimeng.entity.dt.detail;

import java.io.Serializable;
import java.util.List;

public class Dt_dt_dataEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4258533674129004984L;

    private String uc_id;

    private String content;

    private String thumb_img;

    private String u_id;

    private String created_time;

    private Dt_dt_data_upEntity user_post;

    private List<Dt_dt_data_cmEntity> comments;

    private List<Dt_dt_data_clEntity> clicks;

    public Dt_dt_dataEntity() {
        super();
    }

    public Dt_dt_dataEntity(String uc_id, String content, String thumb_img, String u_id,
            String created_time, Dt_dt_data_upEntity user_post, List<Dt_dt_data_cmEntity> comments,
            List<Dt_dt_data_clEntity> clicks) {
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

    public Dt_dt_data_upEntity getUser_post() {
        return user_post;
    }

    public void setUser_post(Dt_dt_data_upEntity user_post) {
        this.user_post = user_post;
    }

    public List<Dt_dt_data_cmEntity> getComments() {
        return comments;
    }

    public void setComments(List<Dt_dt_data_cmEntity> comments) {
        this.comments = comments;
    }

    public List<Dt_dt_data_clEntity> getClicks() {
        return clicks;
    }

    public void setClicks(List<Dt_dt_data_clEntity> clicks) {
        this.clicks = clicks;
    }

    @Override
    public String toString() {
        return "Dt_dt_dataEntity [uc_id=" + uc_id + ", content=" + content + ", thumb_img="
                + thumb_img + ", u_id=" + u_id + ", created_time=" + created_time + ", user_post="
                + user_post + ", comments=" + comments + ", clicks=" + clicks + "]";
    }

}
