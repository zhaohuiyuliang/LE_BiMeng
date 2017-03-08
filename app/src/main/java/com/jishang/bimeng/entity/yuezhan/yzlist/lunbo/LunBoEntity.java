
package com.jishang.bimeng.entity.yuezhan.yzlist.lunbo;

import java.io.Serializable;
import java.util.List;

public class LunBoEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5547978437450062894L;

    private int status;

    private String status_code;

    private List<Data> data;

    public LunBoEntity() {
        super();
    }

    public LunBoEntity(int status, String status_code, List<Data> data) {
        super();
        this.status = status;
        this.status_code = status_code;
        this.data = data;
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LunBoEntity [status=" + status + ", status_code=" + status_code + ", data=" + data
                + "]";
    }

    public class Data implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 9040435828742170045L;

        private String at_id;

        private String title;

        private String content;

        private String img;

        private String url;

        private String status;

        private String create_time;

        public Data() {
            super();
        }

        public Data(String at_id, String title, String content, String img, String url,
                String status, String create_time) {
            super();
            this.at_id = at_id;
            this.title = title;
            this.content = content;
            this.img = img;
            this.url = url;
            this.status = status;
            this.create_time = create_time;
        }

        public String getAt_id() {
            return at_id;
        }

        public void setAt_id(String at_id) {
            this.at_id = at_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        @Override
        public String toString() {
            return "LunboDataEntity [at_id=" + at_id + ", title=" + title + ", content=" + content
                    + ", img=" + img + ", url=" + url + ", status=" + status + ", create_time="
                    + create_time + "]";
        }

    }

}
