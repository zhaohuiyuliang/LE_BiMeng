
package com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian;

import java.io.Serializable;

public class Fenqian_confimEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8340900142377060973L;

    private int status;

    private String errors;

    public Fenqian_confimEntity() {
        super();
    }

    public Fenqian_confimEntity(int status, String errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Fenqian_confimEntity [status=" + status + ", errors=" + errors + "]";
    }

}
