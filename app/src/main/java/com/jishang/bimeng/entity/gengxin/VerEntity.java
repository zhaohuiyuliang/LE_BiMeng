
package com.jishang.bimeng.entity.gengxin;

import java.io.Serializable;

/**
 * @author Xing,Ming
 * @version 2016年5月16日 下午4:05:29
 */
public class VerEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1705931580452484864L;

    private int status;

    private String status_code;

    private String errors;

    private Ver_dataEntity data;

    public VerEntity() {
        super();
    }

    public VerEntity(int status, String status_code, String errors, Ver_dataEntity data) {
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

    public Ver_dataEntity getData() {
        return data;
    }

    public void setData(Ver_dataEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VerEntity [status=" + status + ", status_code=" + status_code + ", errors="
                + errors + ", data=" + data + "]";
    }

}
