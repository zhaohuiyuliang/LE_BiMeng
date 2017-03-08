
package com.jishang.bimeng.utils.db;

/**
 * @author Xing,Ming
 * @version 2016年6月3日 下午6:33:51
 */
public class PersonEntity {

    public String huanid;

    public String name;

    public String img;

    public PersonEntity() {
        super();
    }

    public PersonEntity(String huanid, String name, String img) {
        super();
        this.huanid = huanid;
        this.name = name;
        this.img = img;
    }

    public String getHuanid() {
        return huanid;
    }

    public void setHuanid(String huanid) {
        this.huanid = huanid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "PersonEntity [huanid=" + huanid + ", name=" + name + ", img=" + img + "]";
    }

}
