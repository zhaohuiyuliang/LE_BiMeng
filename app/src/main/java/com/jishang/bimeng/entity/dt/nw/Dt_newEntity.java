
package com.jishang.bimeng.entity.dt.nw;

import java.io.Serializable;
import java.util.List;

public class Dt_newEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2273631788919797518L;

    public int status;

    public String status_code;

    public String errors;

    public Dt_newptEntity pagination;

    public List<Dt_new_dataEntity> data;

    public Dt_newEntity() {
        super();
    }

    public Dt_newEntity(int status, String status_code, String errors, Dt_newptEntity pagination,
            List<Dt_new_dataEntity> data) {
        super();
        this.status = status;
        this.status_code = status_code;
        this.errors = errors;
        this.pagination = pagination;
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

    public Dt_newptEntity getPagination() {
        return pagination;
    }

    public void setPagination(Dt_newptEntity pagination) {
        this.pagination = pagination;
    }

    public List<Dt_new_dataEntity> getData() {
        return data;
    }

    public void setData(List<Dt_new_dataEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Dt_newEntity [status=" + status + ", status_code=" + status_code + ", errors="
                + errors + ", pagination=" + pagination + ", data=" + data + "]";
    }

}
