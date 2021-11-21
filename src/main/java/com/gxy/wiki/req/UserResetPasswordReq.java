package com.gxy.wiki.req;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author GUO
 * @Classname EbookSaveReq
 * @Description TODO
 * @Date 2021/9/24 23:33
 */
public class UserResetPasswordReq {

    private Long id;

    @NotNull(message = "【密码】不能为空")
    @Length(min = 4,message = "【密码】未满足6~20位")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserResetPasswordReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
