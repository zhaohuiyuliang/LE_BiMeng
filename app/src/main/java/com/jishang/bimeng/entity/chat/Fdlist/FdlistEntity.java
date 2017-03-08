
package com.jishang.bimeng.entity.chat.Fdlist;

import java.io.Serializable;
import java.util.List;

public class FdlistEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -816875611310731367L;

    private int status;

    private String status_code;

    private List<FriendEntity> data;

    public FdlistEntity() {
        super();
    }

    public FdlistEntity(int status, String status_code, List<FriendEntity> data) {
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

    public List<FriendEntity> getData() {
        return data;
    }

    public void setData(List<FriendEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FdlistEntity [status=" + status + ", status_code=" + status_code + ", data=" + data
                + "]";
    }

}
