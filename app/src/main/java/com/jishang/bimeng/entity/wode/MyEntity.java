
package com.jishang.bimeng.entity.wode;

import java.io.Serializable;

/**
 * "我的"UI，请求数据对象
 * 
 * @author wangliang Jul 14, 2016
 */
public class MyEntity implements Serializable {
    public int status;

    public String status_code;

    public MyDataEntity data;

    public MyEntity() {
        super();
    }

    public MyEntity(int status, String status_code, MyDataEntity data) {
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

    public MyDataEntity getData() {
        return data;
    }

    public void setData(MyDataEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WodeEntity [status=" + status + ", status_code=" + status_code + ", data=" + data
                + "]";
    }

}
