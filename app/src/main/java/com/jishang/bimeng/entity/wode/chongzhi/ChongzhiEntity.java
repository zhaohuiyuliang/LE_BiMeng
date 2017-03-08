
package com.jishang.bimeng.entity.wode.chongzhi;

import java.io.Serializable;

public class ChongzhiEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1412596584066970464L;

    private int status;

    private String status_code;

    private String errors;

    private Data data;

    public ChongzhiEntity() {
        super();
    }

    public ChongzhiEntity(int status, String status_code, String errors, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChongzhiEntity [status=" + status + ", status_code=" + status_code + ", errors="
                + errors + ", data=" + data + "]";
    }

    public class Data implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 2830181755965981285L;

        private Payment payment;

        private ZFB zfb;

        private WX wx;

        public Data() {
            super();
        }

        public Data(Payment payment, ZFB zfb,
                WX wx) {
            super();
            this.payment = payment;
            this.zfb = zfb;
            this.wx = wx;
        }

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }

        public ZFB getZfb() {
            return zfb;
        }

        public void setZfb(ZFB zfb) {
            this.zfb = zfb;
        }

        public WX getWx() {
            return wx;
        }

        public void setWx(WX wx) {
            this.wx = wx;
        }

        @Override
        public String toString() {
            return "Chongzhi_dataEntiy [payment=" + payment + ", zfb=" + zfb + ", wx=" + wx + "]";
        }

        public class Payment implements Serializable {
            /**
             * 
             */
            private static final long serialVersionUID = 2383304981753577305L;

            private String re_money;

            private String re_uid;

            private String re_order_notice;

            private String re_time;

            private String re_status;

            private String is_recharge;

            private String re_id;

            public Payment() {
                super();
            }

            public Payment(String re_money, String re_uid, String re_order_notice,
                    String re_time, String re_status, String is_recharge, String re_id) {
                super();
                this.re_money = re_money;
                this.re_uid = re_uid;
                this.re_order_notice = re_order_notice;
                this.re_time = re_time;
                this.re_status = re_status;
                this.is_recharge = is_recharge;
                this.re_id = re_id;
            }

            public String getRe_money() {
                return re_money;
            }

            public void setRe_money(String re_money) {
                this.re_money = re_money;
            }

            public String getRe_uid() {
                return re_uid;
            }

            public void setRe_uid(String re_uid) {
                this.re_uid = re_uid;
            }

            public String getRe_order_notice() {
                return re_order_notice;
            }

            public void setRe_order_notice(String re_order_notice) {
                this.re_order_notice = re_order_notice;
            }

            public String getRe_time() {
                return re_time;
            }

            public void setRe_time(String re_time) {
                this.re_time = re_time;
            }

            public String getRe_status() {
                return re_status;
            }

            public void setRe_status(String re_status) {
                this.re_status = re_status;
            }

            public String getIs_recharge() {
                return is_recharge;
            }

            public void setIs_recharge(String is_recharge) {
                this.is_recharge = is_recharge;
            }

            public String getRe_id() {
                return re_id;
            }

            public void setRe_id(String re_id) {
                this.re_id = re_id;
            }

            @Override
            public String toString() {
                return "Chongzhi_data_pmEntity [re_money=" + re_money + ", re_uid=" + re_uid
                        + ", re_order_notice=" + re_order_notice + ", re_time=" + re_time
                        + ", re_status=" + re_status + ", is_recharge=" + is_recharge + ", re_id="
                        + re_id + "]";
            }

        }

        public class ZFB implements Serializable {
            /**
             * 
             */
            private static final long serialVersionUID = 8452044239397336137L;

            private String partner;

            private String seller;

            private String rsa_private;

            private String rsa_public;

            public ZFB() {
                super();
            }

            public ZFB(String partner, String seller, String rsa_private,
                    String rsa_public) {
                super();
                this.partner = partner;
                this.seller = seller;
                this.rsa_private = rsa_private;
                this.rsa_public = rsa_public;
            }

            public String getPartner() {
                return partner;
            }

            public void setPartner(String partner) {
                this.partner = partner;
            }

            public String getSeller() {
                return seller;
            }

            public void setSeller(String seller) {
                this.seller = seller;
            }

            public String getRsa_private() {
                return rsa_private;
            }

            public void setRsa_private(String rsa_private) {
                this.rsa_private = rsa_private;
            }

            public String getRsa_public() {
                return rsa_public;
            }

            public void setRsa_public(String rsa_public) {
                this.rsa_public = rsa_public;
            }

            @Override
            public String toString() {
                return "Chongzhi_data_zfbEntity [partner=" + partner + ", seller=" + seller
                        + ", rsa_private=" + rsa_private + ", rsa_public=" + rsa_public + "]";
            }

        }

        public class WX implements Serializable {
            /**
             * 
             */
            private static final long serialVersionUID = -3622195040781157546L;

            private String appid;

            private String noncestr;

            private String partnerid;

            private String prepayid;

            private String timestamp;

            private String sign;

            public WX() {
                super();
            }

            public WX(String appid, String noncestr, String partnerid,
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
                return "Chongzhi_data_wechatEntity [appid=" + appid + ", noncestr=" + noncestr
                        + ", partnerid=" + partnerid + ", prepayid=" + prepayid + ", timestamp="
                        + timestamp + ", sign=" + sign + "]";
            }

        }

    }

}
