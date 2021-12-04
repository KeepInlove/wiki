package com.gxy.wiki.utils;

import com.gxy.wiki.resp.UserLoginResp;

import java.io.Serializable;

/**
 * @author GUO
 * @Classname LoginUserContext
 * @Description TODO
 * @Date 2021/12/4 16:23
 */
public class LoginUserContext  implements Serializable {

    private static ThreadLocal<UserLoginResp> user = new ThreadLocal<>();

    public static UserLoginResp getUser() {
        return user.get();
    }

    public static void setUser(UserLoginResp user) {
        LoginUserContext.user.set(user);
    }

}
