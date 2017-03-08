
package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;
import java.util.List;

public class YzListEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5419580121369672843L;

    private int status;

    private String status_code;

    private List<List_dataEntity> yz_list;

    private YzList_ptEntity pagination;

    private List_msgEntity message;

    public YzListEntity() {
        super();
    }

    public YzListEntity(int status, String status_code, List<List_dataEntity> yz_list,
            YzList_ptEntity pagination, List_msgEntity message) {
        super();
        this.status = status;
        this.status_code = status_code;
        this.yz_list = yz_list;
        this.pagination = pagination;
        this.message = message;
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

    public List<List_dataEntity> getYz_list() {
        return yz_list;
    }

    public void setYz_list(List<List_dataEntity> yz_list) {
        this.yz_list = yz_list;
    }

    public YzList_ptEntity getPagination() {
        return pagination;
    }

    public void setPagination(YzList_ptEntity pagination) {
        this.pagination = pagination;
    }

    public List_msgEntity getMessage() {
        return message;
    }

    public void setMessage(List_msgEntity message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "YzListEntity [status=" + status + ", status_code=" + status_code + ", yz_list="
                + yz_list + ", pagination=" + pagination + ", message=" + message + "]";
    }

}
