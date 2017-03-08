
package com.jishang.bimeng.entity.yuezhan.confirm.time;

import java.io.Serializable;

public class DataTimeEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5448482073798440798L;

    private String dandt;

    private String start_time;

    public DataTimeEntity() {
        super();
    }

    public DataTimeEntity(String dandt, String start_time) {
        super();
        this.dandt = dandt;
        this.start_time = start_time;
    }

    public String getDandt() {
        return dandt;
    }

    public void setDandt(String dandt) {
        this.dandt = dandt;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    @Override
    public String toString() {
        return "DataTimeEntity [dandt=" + dandt + ", start_time=" + start_time + "]";
    }

}
