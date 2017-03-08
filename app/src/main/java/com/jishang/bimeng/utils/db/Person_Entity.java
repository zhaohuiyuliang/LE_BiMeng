
package com.jishang.bimeng.utils.db;

import java.io.Serializable;

/**
 * @author Xing,Ming
 * @version 2016年6月3日 下午6:33:51
 */
public class Person_Entity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -114216804995385502L;

    private String huanid;

    private String name;

    private String img;

    /**
     * 未读的消息个数
     */
    private int unreadMsgNum;

    private String last;

    public String time_chat_end;

    public String message_chat_end;

    public Person_Entity() {
        super();
    }

    public Person_Entity(String huanid, String name, String img, int weidu, String last) {
        super();
        this.huanid = huanid;
        this.name = name;
        this.img = img;
        this.unreadMsgNum = weidu;
        this.last = last;
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

    public int getWeidu() {
        return unreadMsgNum;
    }

    public void setWeidu(int weidu) {
        this.unreadMsgNum = weidu;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "Person_Entity [huanid=" + huanid + ", name=" + name + ", img=" + img + ", weidu="
                + unreadMsgNum + ", last=" + last + "]";
    }

}
