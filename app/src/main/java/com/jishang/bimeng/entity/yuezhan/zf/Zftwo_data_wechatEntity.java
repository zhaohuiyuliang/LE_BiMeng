
package com.jishang.bimeng.entity.yuezhan.zf;

import java.io.Serializable;

public class Zftwo_data_wechatEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3446195028572175819L;

    private String appid;

    private String noncestr;

    private String partnerid;

    private String prepayid;

    private String timestamp;

    private String sign;

    public Zftwo_data_wechatEntity() {
        super();
    }

    public Zftwo_data_wechatEntity(String appid, String noncestr, String partnerid,
            String prepayid, String timestamp, String sign) {
        super();
        this.appid = appid;
        this.noncestr = noncestr;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.timestamp = timestamp;
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Zftwo_data_wechatEntity [appid=" + appid + ", noncestr=" + noncestr
                + ", partnerid=" + partnerid + ", prepayid=" + prepayid + ", timestamp="
                + timestamp + ", sign=" + sign + "]";
    }

}
