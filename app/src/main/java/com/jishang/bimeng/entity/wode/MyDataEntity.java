
package com.jishang.bimeng.entity.wode;

import java.io.Serializable;

public class MyDataEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 10090908L;

    private String change = "";

    private String income = "";

    private String integral = "";

    public MyDataEntity() {
        super();
    }

    public MyDataEntity(String change, String income, String integral) {
        super();
        this.change = change;
        this.income = income;
        this.integral = integral;
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

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    @Override
    public String toString() {
        return "Wode_dataEntity [change=" + change + ", income=" + income + ", integral="
                + integral + "]";
    }

}
