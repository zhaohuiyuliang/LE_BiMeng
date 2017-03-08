
package com.jishang.bimeng.entity.tonyong;

import java.io.Serializable;

public class TYEntity implements Serializable {
    public int status;

    public String status_code;

    public String errors;

    public TYEntity() {
        super();
    }

    public TYEntity(int status, String status_code, String errors) {
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
