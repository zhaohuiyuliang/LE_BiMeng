
package com.jishang.bimeng.utils.url;

/**
 * @author kangming url统一管理
 */
public class UrlUtils {
    /**
     * 基
     */
//    public static final String BASEURL = "http://apicms.gbimoo.com/";
    public static final String BASEURL = "http://api.gojianghu.com/";

    /**
     * 发送验证码
     */
    public static final String REGISTE_SMS = BASEURL + "v1/sms/verify.json";

    /**
     * 登录
     */
    public static final String LOGIN_URl = BASEURL + "v1/site/login.json";

    /**
     * 性别、个人信息提交
     */
    public static final String REGISTE_SIGNUP = BASEURL + "v1/site/signup.json";

    /**
     * 城市修改
     */
    public static final String REGISTE_SELECT = BASEURL + "v1/business/select.json?";

    /**
     * 等待添加好友的列表
     */
    public static final String WAIT_FRIENDSHIP = BASEURL + "v1/user/wait_friendship.json";

    /**
     * 好友列表
     */
    public static final String CHECK_FRIENDS = BASEURL + "v1/user/check_friends.json";

    /**
     * 同意添加好友
     */
    public static final String AGREE_FRIENDSHIP = BASEURL + "v1/user/agree_friendship.json";

    /**
     * 确认添加好友
     */
    public static final String BUILD_FRIENDSHIP = BASEURL + "v1/user/build_friendship.json";

    public static final String SEARCH = BASEURL + "v1/user/search.json";

}
