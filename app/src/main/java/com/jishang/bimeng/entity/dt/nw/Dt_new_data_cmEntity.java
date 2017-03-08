
package com.jishang.bimeng.entity.dt.nw;

import java.io.Serializable;

/**
 * 评论对象
 * 
 * @author wangliang Jul 14, 2016
 */
public class Dt_new_data_cmEntity implements Serializable {
    private String count;

    private String ucmc_id;

    public Dt_new_data_cmEntity() {
        super();
    }

    public Dt_new_data_cmEntity(String count, String ucmc_id) {
        super();
        this.count = count;
        this.ucmc_id = ucmc_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUcmc_id() {
        return ucmc_id;
    }

    public void setUcmc_id(String ucmc_id) {
        this.ucmc_id = ucmc_id;
    }

    @Override
    public String toString() {
        return "Dt_new_data_cmEntity [count=" + count + ", ucmc_id=" + ucmc_id + "]";
    }

}
