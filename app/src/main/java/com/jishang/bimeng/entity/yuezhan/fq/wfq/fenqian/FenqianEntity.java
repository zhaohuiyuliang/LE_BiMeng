
package com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian;

import java.io.Serializable;
import java.util.List;

public class FenqianEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3547913468626134873L;

    private int status;

    private String status_code;

    private String errors;

    private List<Fenqian_dataEntity> data;

    public FenqianEntity() {
        super();
    }

    public FenqianEntity(int status, String status_code, String errors,
            List<Fenqian_dataEntity> data) {
        super();
        this.status = status;
        this.status_code = status_code;
        this.errors = errors;
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

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public List<Fenqian_dataEntity> getData() {
        return data;
    }

    public void setData(List<Fenqian_dataEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FenqianEntity [status=" + status + ", status_code=" + status_code + ", errors="
                + errors + ", data=" + data + "]";
    }

}
