
package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;

public class List_msgEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8672254305917148655L;

    private String code_num = "";

    private String business_num = "";

    private String yz_num = "";

    /**
     * 参与"我发起的开黑"的人数
     * 
     * @param list
     */
    private String yz_fq_num = "";

    public List_msgEntity() {
        super();
    }

    public List_msgEntity(String code_num, String business_num, String yz_num, String yz_fq_num) {
        super();
        this.code_num = code_num;
        this.business_num = business_num;
        this.yz_num = yz_num;
        this.yz_fq_num = yz_fq_num;
    }

    public String getCode_num() {
        return code_num;
    }

    public void setCode_num(String code_num) {
        this.code_num = code_num;
    }

    public String getBusiness_num() {
        return business_num;
    }

    public void setBusiness_num(String business_num) {
        this.business_num = business_num;
    }

    public String getYz_num() {
        return yz_num;
    }

    public void setYz_num(String yz_num) {
        this.yz_num = yz_num;
    }

    /**
     * 参与"我发起的开黑"的人数
     * 
     * @param list
     */
    public String getYz_fq_num() {
        return yz_fq_num;
    }

    /**
     * 参与"我发起的开黑"的人数
     * 
     * @param list
     */
    public void setYz_fq_num(String yz_fq_num) {
        this.yz_fq_num = yz_fq_num;
    }

    @Override
    public String toString() {
        return "List_msgEntity [code_num=" + code_num + ", business_num=" + business_num
                + ", yz_num=" + yz_num + ", yz_fq_num=" + yz_fq_num + "]";
    }

}
