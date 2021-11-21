package com.gxy.wiki.req;

/**
 * @author GUO
 * @Classname UserQueryReq
 * @Description TODO
 * @Date 2021/11/21 19:59
 */
public class UserQueryReq  extends PageReq {
    private String loginName;
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "UserQueryResp{" +
                "loginName='" + loginName + '\'' +
                '}';
    }
}
