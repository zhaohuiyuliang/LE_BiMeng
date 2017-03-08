
package com.jishang.bimeng.entity.gengxin;

import java.io.Serializable;

/**
 * @author Xing,Ming
 * @version 2016年5月16日 下午4:06:21
 */
public class Ver_dataEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4731912408747877491L;

    private String id;

    private String version;

    private String name;

    private String content;

    private String url;

    private String time;

    public Ver_dataEntity() {
        super();
    }

    public Ver_dataEntity(String id, String version, String name, String url, String time) {
        super();
        this.id = id;
        this.version = version;
        this.name = name;
        this.url = url;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Ver_dataEntity [id=" + id + ", version=" + version + ", name=" + name + ", url="
                + url + ", time=" + time + "]";
    }

}
