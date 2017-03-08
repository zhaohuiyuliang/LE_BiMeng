
package com.jishang.bimeng.entity.yuezhan.zf;

import java.io.Serializable;

public class Zf_list_DataEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1564641042190592253L;

    private String p_id;

    private String yz_id;

    private String order_notice;

    private String money;

    private String change;

    private String income;

    private String uid;

    private String fuid;

    private int change_id;

    private int income_id;

    private int third_id;

    private String ypn_id;

    private String ywp_id;

    private String yw_order_notice;

    private String pnyw_uid;

    private String pnpw_uid;

    public Zf_list_DataEntity() {
        super();
    }

    public Zf_list_DataEntity(String p_id, String yz_id, String order_notice, String money,
            String change, String income, String uid, String fuid, int change_id, int income_id,
            int third_id, String ypn_id, String ywp_id, String yw_order_notice, String pnyw_uid,
            String pnpw_uid) {
        super();
        this.p_id = p_id;
        this.yz_id = yz_id;
        this.order_notice = order_notice;
        this.money = money;
        this.change = change;
        this.income = income;
        this.uid = uid;
        this.fuid = fuid;
        this.change_id = change_id;
        this.income_id = income_id;
        this.third_id = third_id;
        this.ypn_id = ypn_id;
        this.ywp_id = ywp_id;
        this.yw_order_notice = yw_order_notice;
        this.pnyw_uid = pnyw_uid;
        this.pnpw_uid = pnpw_uid;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getYz_id() {
        return yz_id;
    }

    public void setYz_id(String yz_id) {
        this.yz_id = yz_id;
    }

    public String getOrder_notice() {
        return order_notice;
    }

    public void setOrder_notice(String order_notice) {
        this.order_notice = order_notice;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFuid() {
        return fuid;
    }

    public void setFuid(String fuid) {
        this.fuid = fuid;
    }

    public int getChange_id() {
        return change_id;
    }

    public void setChange_id(int change_id) {
        this.change_id = change_id;
    }

    public int getIncome_id() {
        return income_id;
    }

    public void setIncome_id(int income_id) {
        this.income_id = income_id;
    }

    public int getThird_id() {
        return third_id;
    }

    public void setThird_id(int third_id) {
        this.third_id = third_id;
    }

    public String getYpn_id() {
        return ypn_id;
    }

    public void setYpn_id(String ypn_id) {
        this.ypn_id = ypn_id;
    }

    public String getYwp_id() {
        return ywp_id;
    }

    public void setYwp_id(String ywp_id) {
        this.ywp_id = ywp_id;
    }

    public String getYw_order_notice() {
        return yw_order_notice;
    }

    public void setYw_order_notice(String yw_order_notice) {
        this.yw_order_notice = yw_order_notice;
    }

    public String getPnyw_uid() {
        return pnyw_uid;
    }

    public void setPnyw_uid(String pnyw_uid) {
        this.pnyw_uid = pnyw_uid;
    }

    public String getPnpw_uid() {
        return pnpw_uid;
    }

    public void setPnpw_uid(String pnpw_uid) {
        this.pnpw_uid = pnpw_uid;
    }

    @Override
    public String toString() {
        return "Zf_list_DataEntity [p_id=" + p_id + ", yz_id=" + yz_id + ", order_notice="
                + order_notice + ", money=" + money + ", change=" + change + ", income=" + income
                + ", uid=" + uid + ", fuid=" + fuid + ", change_id=" + change_id + ", income_id="
                + income_id + ", third_id=" + third_id + ", ypn_id=" + ypn_id + ", ywp_id="
                + ywp_id + ", yw_order_notice=" + yw_order_notice + ", pnyw_uid=" + pnyw_uid
                + ", pnpw_uid=" + pnpw_uid + "]";
    }

}
