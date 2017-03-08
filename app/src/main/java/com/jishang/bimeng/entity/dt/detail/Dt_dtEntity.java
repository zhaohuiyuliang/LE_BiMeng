
package com.jishang.bimeng.entity.dt.detail;

import java.io.Serializable;

public class Dt_dtEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6265087543655554422L;

    private int status;

    private String status_code;

    private Dt_dt_dataEntity data;

    public Dt_dtEntity() {
        super();
    }

    public Dt_dtEntity(int status, String status_code, Dt_dt_dataEntity data) {
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

    public Dt_dt_dataEntity getData() {
        return data;
    }

    public void setData(Dt_dt_dataEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Dt_dtEntity [status=" + status + ", status_code=" + status_code + ", data=" + data
                + "]";
    }

}
