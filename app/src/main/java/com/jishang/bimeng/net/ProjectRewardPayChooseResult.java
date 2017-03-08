
package com.jishang.bimeng.net;

import java.io.Serializable;

public class ProjectRewardPayChooseResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3158105458040402297L;

    public Data data;

    public class Data {
        /**
         * gift_name、body、uid三个字段为微信支付及支付宝支付共有的参数属性
         */
        public String gift_name;

        public String body;

        public String uid;

        public ZFB zfb;

        /**
         * 以下为微信支付相关参数属性
         */
        /**
         * 订单号
         */
        public String out_trade_no;

        public String total_fee;

        public WX wx;

        public class WX {

            public String appid;

            public String noncestr;

            public String partnerid;

            public String prepayid;

            public String sign;

            // public String package;//该字段是java关键字

            public String timestamp;

        }

        /**
         * 以下为支付宝特有的参数属性
         */
        /**
         * 订单号
         */
        public String order_id;

        public String money;

        public class ZFB {

            public String partner;

            public String seller;

            public String rsa_private;

            public String rsa_public;

            public String timestamp;

        }

    }

}
