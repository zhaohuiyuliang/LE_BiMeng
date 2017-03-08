
package com.jishang.bimeng.entity.yuezhan.zf;

import java.io.Serializable;

public class ZftwoEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1422770499800951294L;

    private int status;

    private String status_code;

    private int error_code;

    private String errors;

    public Data data;

    public ZftwoEntity() {
        super();
    }

    public ZftwoEntity(int status, String status_code, int error_code, String errors, Data data) {
        super();
        this.status = status;
        this.status_code = status_code;
        this.error_code = error_code;
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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ZftwoEntity [status=" + status + ", status_code=" + status_code + ", error_code="
                + error_code + ", errors=" + errors + ", data=" + data + "]";
    }

    public class Data implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -8630875120753162699L;

        private String gift_name;

        private String order_id;

        private String money;

        private String change;

        private String income;

        private String body;

        private String p_id;

        private String uid;

        private String ypn_id;

        private String out_trade_no;

        private String total_fee;

        private String spbill_create_ip;

        private String trade_type;

        private Zftwo_data_zfbEntity zfb;

        private Zftwo_data_wechatEntity wx;

        public Data() {
            super();
        }

        public Data(String gift_name, String order_id, String money, String change, String income,
                String body, String p_id, String uid, String ypn_id, String out_trade_no,
                String total_fee, String spbill_create_ip, String trade_type,
                Zftwo_data_zfbEntity zfb, Zftwo_data_wechatEntity wx) {
            super();
            this.gift_name = gift_name;
            this.order_id = order_id;
            this.money = money;
            this.change = change;
            this.income = income;
            this.body = body;
            this.p_id = p_id;
            this.uid = uid;
            this.ypn_id = ypn_id;
            this.out_trade_no = out_trade_no;
            this.total_fee = total_fee;
            this.spbill_create_ip = spbill_create_ip;
            this.trade_type = trade_type;
            this.zfb = zfb;
            this.wx = wx;
        }

        public String getGift_name() {
            return gift_name;
        }

        public void setGift_name(String gift_name) {
            this.gift_name = gift_name;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getYpn_id() {
            return ypn_id;
        }

        public void setYpn_id(String ypn_id) {
            this.ypn_id = ypn_id;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getSpbill_create_ip() {
            return spbill_create_ip;
        }

        public void setSpbill_create_ip(String spbill_create_ip) {
            this.spbill_create_ip = spbill_create_ip;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public Zftwo_data_zfbEntity getZfb() {
            return zfb;
        }

        public void setZfb(Zftwo_data_zfbEntity zfb) {
            this.zfb = zfb;
        }

        public Zftwo_data_wechatEntity getWx() {
            return wx;
        }

        public void setWx(Zftwo_data_wechatEntity wx) {
            this.wx = wx;
        }

        @Override
        public String toString() {
            return "Zftwo_dataEntity [gift_name=" + gift_name + ", order_id=" + order_id
                    + ", money=" + money + ", change=" + change + ", income=" + income + ", body="
                    + body + ", p_id=" + p_id + ", uid=" + uid + ", ypn_id=" + ypn_id
                    + ", out_trade_no=" + out_trade_no + ", total_fee=" + total_fee
                    + ", spbill_create_ip=" + spbill_create_ip + ", trade_type=" + trade_type
                    + ", zfb=" + zfb + ", wx=" + wx + "]";
        }

    }

}
