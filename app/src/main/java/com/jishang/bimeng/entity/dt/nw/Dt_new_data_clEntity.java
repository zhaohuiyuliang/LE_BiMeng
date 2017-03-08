
package com.jishang.bimeng.entity.dt.nw;

import java.io.Serializable;

/**
 * 点赞对象
 * 
 * @author wangliang Jul 14, 2016
 */
public class Dt_new_data_clEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1592879L;

    private String count;

    private String ucsc_id;

    private String ucsp_id;

    public Dt_new_data_clEntity() {
        super();
    }

    public Dt_new_data_clEntity(String count, String ucsc_id, String ucsp_id) {
        super();
        this.count = count;
        this.ucsc_id = ucsc_id;
        this.ucsp_id = ucsp_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUcsc_id() {
        return ucsc_id;
    }

    public void setUcsc_id(String ucsc_id) {
        this.ucsc_id = ucsc_id;
    }

    public String getUcsp_id() {
        return ucsp_id;
    }

    public void setUcsp_id(String ucsp_id) {
        this.ucsp_id = ucsp_id;
    }

    @Override
    public String toString() {
        return "Dt_new_data_clEntity [count=" + count + ", ucsc_id=" + ucsc_id + ", ucsp_id="
                + ucsp_id + "]";
    }

}
