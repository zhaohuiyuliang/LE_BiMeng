
package com.jishang.bimeng.ui.adapter.mode;

import java.io.Serializable;

/**
 * 点赞成功返回对象
 * 
 * @author wangliang Jul 14, 2016
 */
public class DZResultEntitiy implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1989091L;

    public int status;

    public String status_code;

    public String errors;

    public DZResult data;

    public DZResultEntitiy() {
        super();
    }

    public DZResultEntitiy(int status, String status_code, String errors) {
        super();
        this.status = status;
        this.status_code = status_code;
        this.errors = errors;
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

    @Override
    public String toString() {
        return "TYEntity [status=" + status + ", status_code=" + status_code + ", errors=" + errors
                + "]";
    }
}
