
package com.jishang.bimeng.entity.yuezhan.zf;

import java.io.Serializable;

public class ZfthreeDataEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8351121310812565410L;

    public ZfthreeDataEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ZfthreeDataEntity(String zf_uid) {
        super();
        this.zf_uid = zf_uid;
    }

    private String zf_uid;

    public String getZf_uid() {
        return zf_uid;
    }

    public void setZf_uid(String zf_uid) {
        this.zf_uid = zf_uid;
    }

    @Override
    public String toString() {
        return "ZfthreeDataEntity [zf_uid=" + zf_uid + "]";
    }

}
